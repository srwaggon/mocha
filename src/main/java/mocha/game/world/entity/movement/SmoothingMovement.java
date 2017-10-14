package mocha.game.world.entity.movement;

import mocha.game.InputKey;

public class SmoothingMovement extends SimpleMovement {

  private final double timeSpan = 15.0D;
  private double timeStep = timeSpan;
  private double startLocation;
  private double endLocation;

  @Override
  public void tick() {
    if (timeStep == timeSpan && InputKey.RIGHT.isDown()) {
      timeStep = 0.0;
      startLocation = this.getLocation().getX();
      endLocation = startLocation + 16.0D;
    }

    if (timeStep < timeSpan) {
      timeStep++;
      this.getLocation().setX(startLocation + (endLocation - startLocation) * (timeStep / timeSpan));
    }
  }
}
