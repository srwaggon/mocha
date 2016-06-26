package mocha.game.world.entity;

import org.springframework.stereotype.Component;

import mocha.game.InputKey;

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

    if (InputKey.RIGHT.isDown()) {
      this.setX(this.getX() + this.getSpeed());
    }

    if (InputKey.LEFT.isDown()) {
      this.setX(this.getX() - this.getSpeed());
    }

    if (InputKey.UP.isDown()) {
      this.setY(this.getY() - this.getSpeed());
    }

    if (InputKey.DOWN.isDown()) {
      this.setY(this.getY() + this.getSpeed());
    }

  }
}
