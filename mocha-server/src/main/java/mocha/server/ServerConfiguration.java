package mocha.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

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

  @Bean()
  public TaskService taskService() {
    return new TaskService();
  }

}
