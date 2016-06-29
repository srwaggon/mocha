package mocha.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputKeyTest {

  private InputKey testObject;

  @Before
  public void setUp() throws Exception {
    testObject = new InputKey();
  }

  @Test
  public void key_StartUp() {
    assertFalse(testObject.isDown());
  }

  @Test
  public void key_StaysUpEvenWhenPressed() {
    testObject.down();

    assertFalse(testObject.isDown());
  }

  @Test
  public void tick_SetsKeyStateToDown_WhenKeyWasPressed() {
    testObject.down();

    testObject.tick();

    assertTrue(testObject.isDown());
  }

  @Test
  public void key_StaysDownEvenWhenReleased() {
    testObject.down();
    testObject.tick();

    testObject.up();

    assertTrue(testObject.isDown());
  }

  @Test
  public void tick_SetsKeyStateToUp_WhenKeyIsReleased() {
    testObject.down();
    testObject.tick();
    testObject.up();

    testObject.tick();

    assertFalse(testObject.isDown());
  }

  @Test
  public void keyIsDownForAtLeastOneTick_IfEverPressed() {
    testObject.down();
    testObject.up();
    testObject.tick();

    assertTrue(testObject.isDown());
  }

  @Test
  public void keyIsNotDownAfterSecondTick() {
    testObject.down();
    testObject.up();
    testObject.tick();
    testObject.tick();

    assertFalse(testObject.isDown());
  }

  @Test
  public void keyIsDownForMultipleTicks_EvenWhenPressedOnce() {
    testObject.down();
    testObject.tick();
    testObject.tick();
    testObject.tick();

    assertTrue(testObject.isDown());
  }

  @Test
  public void isClicked_ReturnsFalse() {
    assertFalse(testObject.isClicked());
  }

  @Test
  public void isClicked_ReturnsTrue_WhenKeyClicked() {
    testObject.down();
    testObject.tick();

    assertTrue(testObject.isClicked());
  }

  @Test
  public void isClicked_ReturnsFalseTwoTicks_AfterPress() {
    testObject.down();
    testObject.tick();
    testObject.tick();

    assertFalse(testObject.isClicked());
  }

  @Test
  public void isClicked_ReturnsTrueForEachPress() {
    testObject.down();
    testObject.up();
    testObject.down();
    testObject.tick();
    testObject.tick();

    assertTrue(testObject.isClicked());
  }

  @Test
  public void isClicked_ReturnsFalseAfterBeingHandled() {
    testObject.down();
    testObject.up();
    testObject.down();
    testObject.tick();
    testObject.tick();
    testObject.tick();

    assertFalse(testObject.isClicked());
  }

}