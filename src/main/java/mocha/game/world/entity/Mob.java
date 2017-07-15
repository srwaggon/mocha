package mocha.game.world.entity;

import mocha.gfx.MochaCanvas;

public class Mob extends Entity {

  private double width = 32;
  private double height = 32;
  private double speed = 1.0;

  public Mob() {
    super();
  }

  public Mob(int id) {
    super(id);
  }

  @Override
  public void draw(MochaCanvas mochaCanvas) {
    mochaCanvas.drawSprite(130, getLocation(), 3);
  }

  public double getSpeed() {
    return speed;
  }
}
