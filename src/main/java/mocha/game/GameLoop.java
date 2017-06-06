package mocha.game;


import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.animation.AnimationTimer;

@Component
public class GameLoop extends AnimationTimer {
  private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
  private static final int TICKS_PER_SECOND = 60;
  @Inject
  private Game game;
  private long previousTime;
  private double unprocessed = 0.0;

  private double getTimestepInNanoseconds() {
    return NANOSECONDS_PER_SECOND / TICKS_PER_SECOND;
  }

  @Override
  public void handle(long now) {
    if (previousTime == 0) {
      previousTime = now;
      return;
    }
    accumulateUnprocessedTicks(now);
    previousTime = now;
    processTicks();
  }

  private void accumulateUnprocessedTicks(long now) {
    unprocessed += (now - previousTime) / getTimestepInNanoseconds();
  }

  private void processTicks() {
    while (unprocessed >= 1) {
      game.tick();
      InputKey.tickAll();
      unprocessed -= 1.0;
    }
  }
}
