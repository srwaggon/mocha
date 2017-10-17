package mocha.game.world.entity.movement;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import mocha.game.InputKey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class SlidingMovementTest {

  private SlidingMovement testObject;
  private double distance = 16.0D;
  private int duration = 15;

  @Before
  public void setUp() {
    testObject = new SlidingMovement(distance, duration);
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
      testObject.tick();
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }

  @Test
  public void theMovementContinues_WhenTheKeyRightIsPressed() throws Exception {
    double timeSteps = 10.0D;
    Double expected = distance * (timeSteps / duration);
    testObject.right();

    for (int i = 0; i < timeSteps; i++) {
      testObject.tick();
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
      testObject.tick();
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }

  @Test
  public void theMovementStops_WhenItHasTravelledAFullTile_GivenTheKeyHasBeenReleased() {
    double timeSteps = 30;
    double expected = distance;
    testObject.right();
    testObject.tick();

    for (int i = 1; i < timeSteps; i++) {
      testObject.tick();
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }

  @Test
  public void theMovementWillContinue_WhenToldToMoveAgain() {
    double expected = distance + distance * (1.0 / duration);
    testObject.right();
    for (int i = 0; i < 120; i++) {
      testObject.tick();
    }
    testObject.right();
    testObject.tick();

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }
  // endregion right

  // region down
  @Test
  public void theMovementBegins_WhenMovingDown() {
    double expected = distance * (1.0 / duration);
    testObject.down();

    testObject.tick();

    double actual = testObject.getLocation().getY();
    assertEquals(expected, actual, 0.000001);
  }
  // endregion down

  // region up

  @Test
  public void theMovementBegins_WhenMovingUp() throws Exception {
    double expected = distance * (1.0 / duration) * -1;
    testObject.up();

    testObject.tick();

    double actual = testObject.getLocation().getY();
    assertEquals(expected, actual, 0.000001);
  }

  // endregion up

  // region left

  @Test
  public void theMovementBegins_WhenMovingLeft() throws Exception {
    double expected = distance * (1.0 / duration) * -1;
    testObject.left();

    testObject.tick();

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }
  // endregion left
}