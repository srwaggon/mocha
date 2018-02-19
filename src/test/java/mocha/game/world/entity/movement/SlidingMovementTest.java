package mocha.game.world.entity.movement;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.SimpleCollision;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class SlidingMovementTest {

  private SlidingMovement testObject;
  private double distance = 16.0D;
  private int duration = 15;
  private long now = 0L;

  private EventBus eventBus = new EventBus();

  @Before
  public void setUp() {
    testObject = SlidingMovement.builder()
        .location(new Location())
        .collision(new SimpleCollision())
        .eventBus(eventBus)
        .distance(distance)
        .duration(duration)
        .turns(Lists.newLinkedList())
        .build();
  }

  // region still
  @Test
  public void thereIsNoMovement_WhenNoKeyIsPressed() {
    assertThat(testObject.getLocation().getX()).isEqualTo(0.0);
    assertThat(testObject.getLocation().getY()).isEqualTo(0.0);
  }
  // endregion still

  // region right
  @Test
  public void theMovementBegins_WhenTheKeyRightIsPressed() {
    double timeSteps = 1;
    double expected = distance * (timeSteps / duration);
    testObject.right();

    for (int i = 0; i < timeSteps; i++) {
      testObject.tick(now);
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }

  @Test
  public void theMovementContinues_WhenTheKeyRightIsPressed() {
    double timeSteps = 10.0D;
    Double expected = distance * (timeSteps / duration);
    testObject.right();

    for (int i = 0; i < timeSteps; i++) {
      testObject.tick(now);
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }

  @Test
  public void theMovementContinues_EvenWhenTheKeyIsReleased() {
    double timeSteps = 15;
    double expected = distance * (timeSteps / duration);
    testObject.right();

    for (int i = 0; i < timeSteps; i++) {
      testObject.tick(now);
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }

  @Test
  public void theMovementStops_WhenItHasTravelledAFullTile_GivenTheKeyHasBeenReleased() {
    double timeSteps = 30;
    double expected = distance;
    testObject.right();
    testObject.tick(now);

    for (int i = 1; i < timeSteps; i++) {
      testObject.tick(now);
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }

  @Test
  public void theMovementWillContinue_WhenToldToMoveAgain() {
    double expected = distance + distance * (1.0 / duration);
    testObject.right();
    for (int i = 0; i < 120; i++) {
      testObject.tick(now);
    }
    testObject.right();
    testObject.tick(now);

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }
  // endregion right

  // region down
  @Test
  public void theMovementBegins_WhenMovingDown() {
    double expected = distance * (1.0 / duration);
    testObject.down();

    testObject.tick(now);

    double actual = testObject.getLocation().getY();
    assertEquals(expected, actual, 0.000001);
  }
  // endregion down

  // region up

  @Test
  public void theMovementBegins_WhenMovingUp() {
    double expected = distance * (1.0 / duration) * -1;
    testObject.up();

    testObject.tick(now);

    double actual = testObject.getLocation().getY();
    assertEquals(expected, actual, 0.000001);
  }

  // endregion up

  // region left

  @Test
  public void theMovementBegins_WhenMovingLeft() {
    double expected = distance * (1.0 / duration) * -1;
    testObject.left();

    testObject.tick(now);

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }
  // endregion left
}