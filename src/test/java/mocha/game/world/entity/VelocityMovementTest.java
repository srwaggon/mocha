package mocha.game.world.entity;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.VelocityMovement;

import static org.junit.Assert.*;

public class VelocityMovementTest {
  private double speed = 6;
  private Location location;
  private VelocityMovement testObject;

  private long now = 0L;

  @Before
  public void setUp() throws Exception {
    location = new Location();
    testObject = new VelocityMovement();
    testObject.setLocation(location);
    testObject.setSpeed(speed);
  }

  @Test
  public void setSpeed_SetsTheSpeed() {
    double expectedSpeed = 8;
    testObject.setSpeed(expectedSpeed);

    assertEquals(expectedSpeed, testObject.getSpeed(), 0.0);
  }

  @Test
  public void up_DecreasesTheDYBySpeed() {
    testObject.up();

    assertEquals(-speed, testObject.getYVelocity(), 0);
  }

  @Test
  public void down_IncreasesTheDYBySpeed() {
    testObject.down();

    assertEquals(speed, testObject.getYVelocity(), 0);
  }

  @Test
  public void left_decreasesTheDXBySpeed() {
    testObject.left();

    assertEquals(-speed, testObject.getXVelocity(), 0);
  }
  @Test
  public void right_IncreasesTheDXBySpeed() {
    testObject.right();

    assertEquals(speed, testObject.getXVelocity(), 0);
  }

  @Test
  public void setDx_DoesNotUpdateX() {
    double x = location.getX();

    testObject.setXVelocity(1.0);

    assertEquals(x, location.getX(), 0);
  }

  @Test
  public void setDy_DoesNotUpdateY() {
    double y = location.getY();

    testObject.setYVelocity(1.0);

    assertEquals(y, location.getY(), 0);
  }

  @Test
  public void applyDx_UpdatesXByDifferenceInX() throws Exception {
    double x = location.getX();
    double dx = 1.0;
    testObject.setXVelocity(dx);

    testObject.applyXVelocity();

    assertEquals(x + dx, location.getX(), 0.0);
  }

  @Test
  public void applyDy_UpdatesYByDifferenceInY() throws Exception {
    double y = location.getY();
    double dy = 1.0;
    testObject.setYVelocity(dy);

    testObject.applyDy();

    assertEquals(y + dy, location.getY(), 0.0);
  }

  @Test
  public void tick_ResetsDxAfterMoving() {
    testObject.setXVelocity(1.0);
    testObject.applyXVelocity();

    testObject.tick(now);

    assertEquals(0.0, testObject.getXVelocity(), 0.0);
  }

  @Test
  public void tick_ResetsDyAfterMoving() {
    testObject.setYVelocity(1.0);
    testObject.applyDy();

    testObject.tick(now);

    assertEquals(0.0, testObject.getYVelocity(), 0.0);
  }


}