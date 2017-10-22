package mocha.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.animation.AnimationTimer;
import lombok.extern.slf4j.Slf4j;
import mocha.game.GameView;

@Component
@Slf4j
public class RenderLoop extends AnimationTimer {

  private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
  private static final int FRAMES_PER_SECOND = 60;
  private static final double FRAME_LIFESPAN = NANOSECONDS_PER_SECOND / FRAMES_PER_SECOND;

  @Inject
  private GameView gameView;
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
    gameView.draw(mochaCanvas, 0, 0);

    mochaCanvas.render();

    last = now;
  }

  private void printFramesPerSecond(long now) {
    if (now - lastSecond >= NANOSECONDS_PER_SECOND) {
      log.info("frames per second: {}", renders);
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
