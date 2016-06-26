package mocha.game;


import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

public class GameLoop extends AnimationTimer {
  private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
  private static final int TICKS_PER_SECOND = 60;
  private final Game game;
  private final Canvas canvas;
  private long previousTime;
  private double unprocessed;

  public GameLoop(Game game, Canvas canvas) {
    this.game = game;
    this.canvas = canvas;
    unprocessed = 0;
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
    processTicks();
    game.render(canvas.getGraphicsContext2D());
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
