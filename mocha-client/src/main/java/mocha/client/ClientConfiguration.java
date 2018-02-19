package mocha.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.net.PacketListenerFactory;

@Configuration
public class ClientConfiguration {

  @Bean
  public PacketListenerFactory getPacketListenerFactory() {
    return new PacketListenerFactory();
  }
}
