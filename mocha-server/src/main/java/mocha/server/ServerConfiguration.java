package mocha.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import javax.inject.Inject;

import mocha.game.GameSetup;
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
  public CommandLineRunner gameSetup(GameSetup[] gameSetups) {
    return args -> Arrays.stream(gameSetups).forEach(Runnable::run);
  }

}
