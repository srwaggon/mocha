package mocha.client;

import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.client.gfx.RenderLoop;
import mocha.game.CommandHandler;
import mocha.game.GameLoop;
import mocha.game.RuleService;
import mocha.game.event.MochaEventHandler;
import mocha.game.rule.GameRule;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.collision.CollisionFactory;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.rule.MovementRule;
import mocha.game.world.entity.rule.PickUpItemsRule;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;
import mocha.shared.task.TaskService;

@Configuration
public class ClientConfiguration {

  @Value("${mocha.client.online}")
  private boolean isOnline;

  @Inject
  private ClientEventBus clientEventBus;

  @Bean
  public CommandLineRunner connectEventHandlers(
      ClientEventBus eventBus,
      List<MochaEventHandler> eventHandlers
  ) {
    return args -> eventHandlers.forEach(eventBus::register);
  }

  @Bean
  public CommandLineRunner startUp(TaskService taskService, GameLoop gameLoop, RuleService ruleService, NetworkClient networkClient, RenderLoop renderLoop, ClientPacketResolver clientPacketHandler, @Value("${mocha.client.online}") boolean isOnline) {
    return args -> {
      if (isOnline) {
        taskService.submit(clientPacketHandler);
        networkClient.connectToServer();
      }
      taskService.submit(gameLoop::start);
      gameLoop.submit(ruleService);
      renderLoop.start();
    };
  }

  @Bean
  public CommandLineRunner connectCommandHandlers(ClientEventBus eventBus, List<CommandHandler> commandHandlers) {
    return args -> commandHandlers.forEach(eventBus::register);
  }

  @Bean
  public CollisionFactory collisionFactory(ChunkService chunkService, EntitiesInChunkService entitiesInChunkService) {
    return new CollisionFactory(chunkService, entitiesInChunkService);
  }

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory, Repository<Entity, Integer> entityRepository) {
    return new MovementFactory(collisionFactory, entityRepository);
  }

  @Bean
  public Repository<Movement, Integer> movementRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public RuleService ruleService(List<GameRule> gameRules) {
    return new RuleService(gameRules);
  }

  @Bean
  public GameLoop getGameLoop() {
    return new GameLoop();
  }

  @Bean
  public List<GameRule> getRules(
      Repository<Entity, Integer> entityRepository,
      Repository<Movement, Integer> movementRepository,
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService, EntityService entityService) {
    MovementRule movementRule = new MovementRule(entityRepository, movementRepository, chunkService, entitiesInChunkService);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(chunkService, entitiesInChunkService, movementRepository, entityService);
    clientEventBus.register(pickUpItemsRule);

    return Lists.newArrayList(
        movementRule,
        pickUpItemsRule
    );
  }

  @Bean
  public PacketSenderFactory packetSenderFactory() {
    return new PacketSenderFactory(clientEventBus);
  }

  @Bean()
  public TaskService taskService() {
    TaskService taskService = new TaskService();
    clientEventBus.register(taskService);
    return taskService;
  }

  @Bean
  @Scope("prototype")
  Logger logger(InjectionPoint injectionPoint) {
    return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
  }

}
