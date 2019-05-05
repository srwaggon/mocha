package mocha.net;

import com.google.common.collect.Maps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import mocha.net.packet.MochaConnection;

@Configuration
public class ServerNetConfiguration {

  @Bean
  public Map<Integer, MochaConnection> mochaConnectionsByPlayerId() {
    return Maps.newConcurrentMap();
  }
}
