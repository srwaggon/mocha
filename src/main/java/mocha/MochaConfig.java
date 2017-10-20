package mocha;

import com.google.common.eventbus.EventBus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MochaConfig {

  @Bean
  public EventBus getEventBus() {
    return new EventBus();
  }
}
