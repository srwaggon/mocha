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

    resetInputKeys();
  }

  private void resetInputKeys() {
    Arrays.stream(InputKey.values()).forEach(InputKey::up);
    for (int i = 0; i < 10; i++) {
      InputKey.tickAll();
    }
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
    InputKey.RIGHT.down();
    InputKey.tickAll();

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
    InputKey.RIGHT.down();
    InputKey.tickAll();

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
    InputKey.RIGHT.down();
    InputKey.tickAll();
    testObject.tick();
    InputKey.RIGHT.up();
    InputKey.tickAll();

    for (int i = 1; i < timeSteps; i++) {
      testObject.tick();
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }

  @Test
  public void theMovementStops_WhenItHasTravelledAFullTile_GivenTheKeyHasBeenReleased() {
    double timeSteps = 30;
    double expected = distance * (duration / duration);
    InputKey.RIGHT.down();
    InputKey.tickAll();
    testObject.tick();
    InputKey.RIGHT.up();
    InputKey.tickAll();

    for (int i = 1; i < timeSteps; i++) {
      testObject.tick();
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }

  @Test
  public void theMovementWillContinue_WhenToldToMoveAgain() {
    double expected = 0 + distance + distance * (1.0 / duration);
    InputKey.RIGHT.down();
    InputKey.tickAll();
    testObject.tick();
    InputKey.RIGHT.up();
    InputKey.tickAll();
    for (int i = 1; i < 120; i++) {
      testObject.tick();
    }
    InputKey.RIGHT.down();
    InputKey.tickAll();

    testObject.tick();

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }
  // endregion right

  // region down
  @Test
  public void theMovementBegins_WhenTheKeyDownIsPressed() {
    double timeSteps = 1;
    double expected = distance * (timeSteps / duration);
    InputKey.DOWN.down();
    InputKey.tickAll();

    for (int i = 0; i < timeSteps; i++) {
      testObject.tick();
    }

    double actual = testObject.getLocation().getY();
    assertEquals(expected, actual, 0.000001);
  }
  // endregion down

  // region up

  @Test
  public void theMovementBegins_WhenTheUpKeyIsPressed() throws Exception {
    double timeSteps = 1;
    double expected = distance * (timeSteps / duration) * -1;
    InputKey.UP.down();
    InputKey.tickAll();

    for (int i = 0; i < timeSteps; i++) {
      testObject.tick();
    }

    double actual = testObject.getLocation().getY();
    assertEquals(expected, actual, 0.000001);
  }

  // endregion up

  // region left

  @Test
  public void theMovementBegins_WhenTheLeftKeyIsPressed() throws Exception {
    double timeSteps = 1;
    double expected = distance * (timeSteps / duration) * -1;
    InputKey.LEFT.down();
    InputKey.tickAll();

    for (int i = 0; i < timeSteps; i++) {
      testObject.tick();
    }

    double actual = testObject.getLocation().getX();
    assertEquals(expected, actual, 0.000001);
  }
  // endregion left
}