package mocha.game;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.input.KeyCode;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class InputHandlerTest {
  private InputHandler testObject;
  @Before
  public void setUp() throws Exception {
    testObject = new InputHandler();
    testObject.put(KeyCode.UP, InputKey.UP);
  }

  @Test
  public void getKey_ReturnsTheGameKeyAssociatedWithTheSystemKey() {
    testObject.getKey(KeyCode.UP);
  }

  @Test
  public void isDown_ReturnsFalseByDefault() {
    assertFalse(testObject.isDown(InputKey.UP));
  }

  @Test
  public void down_setsKeyStateToDown_WhenKeyPressed() {
    testObject.down(KeyCode.UP);
    InputKey.UP.tick();

    assertTrue(testObject.isDown(InputKey.UP));
  }

  @Test
  public void up_setsKeyStateToUp_WhenKeyReleased() {
    testObject.down(KeyCode.UP);
    InputKey.UP.tick();

    testObject.up(KeyCode.UP);
    InputKey.UP.tick();

    assertFalse(testObject.isDown(InputKey.UP));
  }

}