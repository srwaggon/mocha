package mocha.gfx.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import mocha.game.world.entity.Entity;
import mocha.gfx.sprite.SpriteSheet;

public class PlayerView extends Canvas {

  private Entity player;
  private SpriteSheet spriteSheet;

  public PlayerView(Entity player, SpriteSheet spriteSheet) {
    super();
    this.player = player;
    this.spriteSheet = spriteSheet;
  }

  public void render() {
    this.setLayoutX(512 / 2);
    this.setLayoutY(512 / 2);
    this.setWidth(32);
    this.setHeight(32);
    Image sprite = spriteSheet.getSprite(player.getSpriteId(), 2.0);
    this.getGraphicsContext2D().drawImage(sprite, 0, 0);
  }

}
