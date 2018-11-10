package mocha.client.gfx.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import mocha.client.gfx.RenderLoop;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.game.world.entity.Entity;

public class EntityView extends Canvas {

  private Entity entity;
  private SpriteSheet spriteSheet;

  public EntityView(Entity entity, SpriteSheet spriteSheet) {
    this.entity = entity;
    this.spriteSheet = spriteSheet;
    this.setOnMouseClicked(event -> System.out.println(this.entity + " clicked!"));
  }

  public void render(long now) {
    this.setLayoutX(entity.getLocation().getX());
    this.setLayoutY(entity.getLocation().getY());
    this.setWidth(32);
    this.setHeight(32);
    Image sprite = getFrame(now);
    this.getGraphicsContext2D().drawImage(sprite, 0, 0);
  }

  private Image getFrame(long now) {
    int spriteId = getSpriteId(now);

    return spriteSheet.getSprite(spriteId, entity.getScale());
  }

  private int getSpriteId(long now) {
    int spriteId = entity.getSpriteId();
    if (!entity.getMovement().isMoving()) {
      return spriteId;
    }

    double frameId = now / RenderLoop.FRAME_LIFESPAN;
    return frameId % 16 < 8 ? spriteId : spriteId + 1;
  }

}
