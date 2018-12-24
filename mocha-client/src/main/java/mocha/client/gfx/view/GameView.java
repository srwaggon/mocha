package mocha.client.gfx.view;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import mocha.client.gfx.BackgroundLayer;
import mocha.client.gfx.SpriteLayer;

@Component
public class GameView extends Canvas {

  private BackgroundLayer backgroundLayer;
  private SpriteLayer spriteLayer;

  @Inject
  public GameView(BackgroundLayer backgroundLayer, SpriteLayer spriteLayer, Camera camera) {
    this.setWidth(1024);
    this.setHeight(768);
    this.backgroundLayer = backgroundLayer;
    this.spriteLayer = spriteLayer;
    camera.widthProperty().bind(this.widthProperty());
    camera.heightProperty().bind(this.heightProperty());
  }

  public void render(long now) {
    GraphicsContext graphics = getGraphicsContext2D();
    backgroundLayer.render(now, graphics);
    spriteLayer.render(now, graphics);
  }

}
