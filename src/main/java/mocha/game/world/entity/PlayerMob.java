package mocha.game.world.entity;

import org.springframework.stereotype.Component;


@Component
public class PlayerMob extends Mob {

  public PlayerMob() {
    super();
  }

  public PlayerMob(int id) {
    super(id);
  }

  @Override
  public void tick() {
    super.tick();

  }

}
