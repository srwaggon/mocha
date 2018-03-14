package mocha.game.world.entity.brain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import mocha.game.world.entity.Entity;

@Builder
@NoArgsConstructor
public class SimpleBrain implements Brain {
  @Override
  public void tick(long now) {

  }

  @Override
  public void setEntity(Entity entity) {

  }
}
