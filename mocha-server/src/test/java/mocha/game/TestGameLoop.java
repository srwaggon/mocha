package mocha.game;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestGameLoop extends GameLoop {
  @Override
  public void start() {
    step(1);
  }

  @Override
  public void handle(long now) {
    super.handle(now);
  }

  public void stepUntil(long time) {
    handle(time);
  }

  @SuppressWarnings("WeakerAccess")
  public void step(int stepCount) {
    long unprocessedTime = (long) (stepCount * getTimestepInNanoseconds());
    long now = previousTime + unprocessedTime;
    handle(now);
  }
}
