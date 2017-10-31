package mocha.game.world.entity.movement;

import com.google.common.eventbus.EventBus;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collision;
import mocha.game.world.entity.movement.collision.SimpleCollision;

import static org.junit.Assert.assertEquals;

public class VelocityMovementTest {
  private double speed = 6;
  private VelocityMovement testObject;

  private long now = 0L;

  private EventBus eventBus = new EventBus();

  @Before
  public void setUp() throws Exception {
    Collision collision = new SimpleCollision();
    testObject = VelocityMovement.builder()
        .eventBus(eventBus)
        .location(new Location())
        .speed(speed)
        .collision(collision)
        .build();
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
    Location location = testObject.getLocation();
    double x = location.getX();

    testObject.setXVelocity(1.0);

    assertEquals(x, location.getX(), 0);
  }

  @Test
  public void setDy_DoesNotUpdateY() {
    Location location = testObject.getLocation();
    double y = location.getY();

    testObject.setYVelocity(1.0);

    assertEquals(y, location.getY(), 0);
  }

  @Test
  public void tick_UpdatesXByTheXVelocity() throws Exception {
    Location location = testObject.getLocation();
    double x = location.getX();
    double dx = 1.0;
    testObject.setXVelocity(dx);

    testObject.tick(now);

    assertEquals(x + dx, location.getX(), 0.0);
  }

  @Test
  public void tick_UpdatesYByTheYVelocity() throws Exception {
    Location location = testObject.getLocation();
    double y = location.getY();
    double dy = 1.0;
    testObject.setYVelocity(dy);

    testObject.tick(now);

    assertEquals(y + dy, location.getY(), 0.0);
  }

  @Test
  public void tick_ResetsDxAfterMoving() {
    testObject.setXVelocity(1.0);

    testObject.tick(now);

    assertEquals(0.0, testObject.getXVelocity(), 0.0);
  }

  @Test
  public void tick_ResetsDyAfterMoving() {
    testObject.setYVelocity(1.0);

    testObject.tick(now);

    assertEquals(0.0, testObject.getYVelocity(), 0.0);
  }

}