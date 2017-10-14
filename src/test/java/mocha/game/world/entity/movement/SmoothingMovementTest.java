package mocha.game.world.entity.movement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import mocha.game.InputKey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SmoothingMovementTest {

  private SmoothingMovement testObject;

  @Before
  public void setUp() {
    testObject = new SmoothingMovement();
  }

  @Test
  public void thereIsNoMovement_WhenNoKeyIsPressed() {
    assertThat(testObject.getLocation().getX()).isEqualTo(0.0);
    assertThat(testObject.getLocation().getY()).isEqualTo(0.0);
  }

  @Test
  public void theMovementBegins_WhenTheKeyRightIsPressed() {
    int timeSteps = 1;
    double expected = 16 * (timeSteps / 15.0D);
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
    Double expected = (16.0D - 0.0D) * (timeSteps / 15.0D);
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
    int timeSteps = 15;
    double expected = 16 * (timeSteps / 15.0D);
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
    int timeSteps = 120;
    double expected = 16 * (15.0D / 15.0D);
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
    double expected = 0 + 16 + 16 * (1.0 / 15.0D);
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
}