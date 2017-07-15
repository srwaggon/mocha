package mocha.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.animation.AnimationTimer;
import mocha.game.Game;

@Component
public class RenderLoop extends AnimationTimer {

  private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
  private static final int FRAMES_PER_SECOND = 60;
  private static final double FRAME_LIFESPAN = NANOSECONDS_PER_SECOND / FRAMES_PER_SECOND;

  @Inject
  private Game game;
  @Inject
  private MochaCanvas mochaCanvas;

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
    game.draw(mochaCanvas);

    mochaCanvas.render();

    last = now;
  }

  private void printFramesPerSecond(long now) {
    if (now - lastSecond >= NANOSECONDS_PER_SECOND) {
      System.out.printf("FPS: %d\n", renders);
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
