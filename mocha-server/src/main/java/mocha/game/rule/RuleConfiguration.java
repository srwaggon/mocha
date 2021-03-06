package mocha.game.rule;

import com.google.common.collect.Lists;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import javax.inject.Inject;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.chunk.tile.rule.GrassGrowsRule;
import mocha.game.world.chunk.tile.rule.WaterEvaporatesRule;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.rule.MovementRule;
import mocha.game.world.entity.rule.PickUpItemsRule;
import mocha.server.event.ServerEventBus;
import mocha.shared.Repository;

@Configuration
public class RuleConfiguration {

  @Inject
  private ServerEventBus serverEventBus;

  @Bean
  public List<GameRule> getRules(
      Repository<Chunk, Integer> chunkRepository,
      Repository<Movement, Integer> movementRepository,
      ChunkService chunkService,
      EntityService entityService
  ) {
    MovementRule movementRule = new MovementRule(movementRepository, chunkService, entityService);
    serverEventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(chunkService, entityService);
    serverEventBus.register(pickUpItemsRule);

    GrassGrowsRule grassGrowsRule = new GrassGrowsRule(chunkRepository, chunkService);

    WaterEvaporatesRule waterEvaporatesRule = new WaterEvaporatesRule(chunkRepository, chunkService);

    return Lists.newArrayList(
        movementRule,
        grassGrowsRule,
        waterEvaporatesRule,
        pickUpItemsRule
    );
  }

}
