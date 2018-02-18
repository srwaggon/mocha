package mocha.gfx.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import mocha.game.world.entity.Entity;
import mocha.gfx.sprite.SpriteSheet;

public class EntityView extends Canvas {

  private Entity entity;
  private Entity player;
  private SpriteSheet spriteSheet;

  public EntityView(Entity entity, Entity player, SpriteSheet spriteSheet) {
    this.entity = entity;
    this.player = player;
    this.spriteSheet = spriteSheet;
    this.setOnMouseClicked(event -> System.out.println(this.entity + " clicked!"));
  }

  public void render() {
    this.setLayoutX(getXOffset());
    this.setLayoutY(getYOffset());
    this.setWidth(32);
    this.setHeight(32);
    Image sprite = spriteSheet.getSprite(entity.getSpriteId(), entity.getScale());
    this.getGraphicsContext2D().drawImage(sprite, 0, 0);
  }

  private double getXOffset() {
    double entityX = entity.getMovement().getLocation().getX();
    double playerX = player.getMovement().getLocation().getX();
    int canvasXOffset = 512 / 2;
    return entityX - playerX + canvasXOffset;
  }

  private double getYOffset() {
    double entityY = entity.getMovement().getLocation().getY();
    double playerY = player.getMovement().getLocation().getY();
    int canvasYOffset = 512 / 2;
    return entityY - playerY + canvasYOffset;
  }
}
