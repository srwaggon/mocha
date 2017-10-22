package mocha.game.world.entity;

import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

public class EntityView implements Drawable {

  private Entity entity;

  public EntityView(Entity entity) {
    this.entity = entity;
  }

  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    mochaCanvas.drawSprite(entity.getSpriteId(), x, y, entity.getScale());
  }
}
