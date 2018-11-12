package mocha.game.world.entity;

public class Pickaxe extends Entity {
  @Override
  public int getSpriteId() {
    return 100;
  }

  @Override
  public boolean isBlocking() {
    return false;
  }
}
