package mocha.game.world.entity.movement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;

@Configuration
public class MovementConfiguration {

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory, Repository<Entity, Integer> entityRepository) {
    return new MovementFactory(collisionFactory, entityRepository);
  }

  @Bean
  public Repository<Movement, Integer> movementRepository() {
    return new InMemoryRepository<>();
  }
}
