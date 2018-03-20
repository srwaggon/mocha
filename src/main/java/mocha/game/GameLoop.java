package mocha.game;


import com.google.common.collect.Sets;

import java.util.Set;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
  private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
  private static final int TICKS_PER_SECOND = 60;
  private long previousTime;
  private double unprocessed = 0.0;

  private Set<Tickable> tickables = Sets.newHashSet();

  private double getTimestepInNanoseconds() {
    return NANOSECONDS_PER_SECOND / TICKS_PER_SECOND;
  }

  public void submit(Tickable tickable) {
    tickables.add(tickable);
  }

  @Override
  public void handle(long now) {
    if (previousTime == 0) {
      previousTime = now;
      return;
    }
    accumulateUnprocessedTicks(now);
    previousTime = now;
    processTicks(now);
  }

  private void accumulateUnprocessedTicks(long now) {
    unprocessed += (now - previousTime) / getTimestepInNanoseconds();
  }

  private void processTicks(long now) {
    while (unprocessed >= 1) {
      tickables.forEach(tickable -> tickable.tick(now));
      unprocessed -= 1.0;
    }
  }
}
