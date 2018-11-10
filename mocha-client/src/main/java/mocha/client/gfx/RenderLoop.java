package mocha.client.gfx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.animation.AnimationTimer;
import mocha.client.Client;
import mocha.client.gfx.view.GameView;

@Component
public class RenderLoop extends AnimationTimer {

  private final Logger log = LoggerFactory.getLogger(Client.class);
  private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
  private static final int FRAMES_PER_SECOND = 60;
  public static final double FRAME_LIFESPAN = NANOSECONDS_PER_SECOND / FRAMES_PER_SECOND;

  @Inject
  private GameView gameView;

  private long last = 0L;
  private int renders;
  private long lastSecond;

  @Override
  public void handle(long now) {
//    printFramesPerSecond(now);
    render(now);
  }

  private void render(long now) {
    if (skipRendering(now)) {
      return;
    }

    gameView.render(now);
    renders++;

    last = now;
  }

  private void printFramesPerSecond(long now) {
    if (now - lastSecond >= NANOSECONDS_PER_SECOND) {
      log.info("frames per second: {}", renders);
      renders = 0;
      lastSecond = now;
    }
  }

  private boolean skipRendering(long now) {
    return now - last <= FRAME_LIFESPAN;
  }
}
