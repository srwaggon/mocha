package mocha.game.world.entity;

public class Mob extends Entity {

  public Mob() {
  }

  public Mob(int id) {
    super(id);
  }

  @Override
  public int getSpriteId() {
    return 130;
  }

  @Override
  public double getScale() {
    return 2.0;
  }
}
