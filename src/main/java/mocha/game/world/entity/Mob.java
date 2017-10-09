package mocha.game.world.entity;

import mocha.gfx.MochaCanvas;

public class Mob extends Entity {

  public Mob() {
  }

  public Mob(int id) {
    super(id);
  }

  @Override
  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    double scale = 2.0;
    mochaCanvas.drawSprite(130, getMovementComponent().getLocation(), scale);
  }
}
