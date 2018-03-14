package mocha.game;


import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
  private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
  private static final int TICKS_PER_SECOND = 60;
  private Game game;
  private long previousTime;
  private double unprocessed = 0.0;

  public GameLoop(Game game) {
    this.game = game;
  }

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
    processTicks(now);
  }

  private void accumulateUnprocessedTicks(long now) {
    unprocessed += (now - previousTime) / getTimestepInNanoseconds();
  }

  private void processTicks(long now) {
    while (unprocessed >= 1) {
      game.tick(now);
      unprocessed -= 1.0;
      System.out.println(unprocessed);
    }
  }
}
