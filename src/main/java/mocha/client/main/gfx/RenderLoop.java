package mocha.client.main.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import mocha.game.Game;

@Component
public class RenderLoop extends AnimationTimer {

  private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
  private static final int FRAMES_PER_SECOND = 60;
  private static final double FRAME_LIFESPAN = NANOSECONDS_PER_SECOND / FRAMES_PER_SECOND;

  @Inject
  private Game game;
  @Inject
  private Canvas canvas;

  private long last = 0L;
  private int renders;
  private long lastSecond;

  @Override
  public void handle(long now) {
    render(now);
//    printFramesPerSecond(now);
  }

  private void render(long now) {
    if (!shouldRender(now)) {
      return;
    }
    game.render(canvas.getGraphicsContext2D());
    last = now;
  }

  private void printFramesPerSecond(long now) {
    if (now - lastSecond >= NANOSECONDS_PER_SECOND) {
      System.out.println(renders);
      renders = 0;
      lastSecond = now;
    }
    if (!shouldRender(now)) {
      return;
    }
    renders++;
  }

  private boolean shouldRender(long now) {
    return now - last >= FRAME_LIFESPAN;
  }
}
