import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import mocha.game.world.entity.Entity;
import mocha.game.world.item.ItemSetup;
import mocha.shared.Repository;

@Configuration
public class TestConfiguration {


  @Bean
  @Profile("test")
  public ItemSetup itemSetup() {
    return new ItemSetup() {
      @Override
      public void run() {
      }
    };
  }

  @Bean
  public CommandLineRunner clearEntities(Repository<Entity, Integer> entityRepository) {
    return args -> entityRepository.deleteAll();
  }

}