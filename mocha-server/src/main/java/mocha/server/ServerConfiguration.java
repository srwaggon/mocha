package mocha.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import javax.inject.Inject;

import mocha.game.CommandHandler;
import mocha.game.GameSetup;
import mocha.game.event.MochaEventHandler;
import mocha.net.packet.PacketSenderFactory;
import mocha.server.event.ServerEventBus;
import mocha.shared.task.TaskService;

@Configuration
public class ServerConfiguration {

  @Inject
  private ServerEventBus serverEventBus;

  @Bean
  public PacketSenderFactory packetSenderFactory() {
    return new PacketSenderFactory(serverEventBus);
  }

  @Bean
  public TaskService taskService() {
    return new TaskService();
  }

  @Bean
  public CommandLineRunner gameSetup(List<GameSetup> gameSetups) {
    return args -> gameSetups.forEach(Runnable::run);
  }

  @Bean
  public CommandLineRunner connectEventHandlers(ServerEventBus eventBus, List<MochaEventHandler> mochaEventHandlers) {
    return args -> mochaEventHandlers.forEach(eventBus::register);
  }

  @Bean
  public CommandLineRunner connectCommandHandlers(ServerEventBus eventBus, List<CommandHandler> commandHandlers) {
    return args -> commandHandlers.forEach(eventBus::register);
  }

}
