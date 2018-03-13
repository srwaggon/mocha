package mocha.client.gfx.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
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

  public void render() {
    this.setLayoutX(entity.getMovement().getLocation().getX());
    this.setLayoutY(entity.getMovement().getLocation().getY());
    this.setWidth(32);
    this.setHeight(32);
    Image sprite = spriteSheet.getSprite(entity.getSpriteId(), entity.getScale());
    this.getGraphicsContext2D().drawImage(sprite, 0, 0);
  }

}
