package mocha.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class InputKeyTest {

  private InputKey testObject;

  @Before
  public void setUp() {
    testObject = InputKey.UNBOUND;
  }

  @After
  public void tearDown() {
    testObject.up();
    for (int i = 0; i < 10; i++) {
      testObject.tick();
    }
  }

  @Test
  public void key_StartUp() {
    assertThat(testObject.isDown()).isFalse();
  }

  @Test
  public void key_StaysUpEvenWhenPressed() {
    testObject.down();

    assertThat(testObject.isDown()).isFalse();
  }

  @Test
  public void tick_SetsKeyStateToDown_WhenKeyWasPressed() {
    testObject.down();

    testObject.tick();

    assertThat(testObject.isDown()).isTrue();
  }

  @Test
  public void key_StaysDownEvenWhenReleased() {
    testObject.down();
    testObject.tick();

    testObject.up();

    assertThat(testObject.isDown()).isTrue();
  }

  @Test
  public void tick_SetsKeyStateToUp_WhenKeyIsReleased() {
    testObject.down();
    testObject.tick();
    testObject.up();

    testObject.tick();

    assertThat(testObject.isDown()).isFalse();
  }

  @Test
  public void keyIsDownForAtLeastOneTick_IfEverPressed() {
    testObject.down();
    testObject.up();
    testObject.tick();

    assertThat(testObject.isDown()).isTrue();
  }

  @Test
  public void keyIsNotDownAfterSecondTick() {
    testObject.down();
    testObject.up();
    testObject.tick();
    testObject.tick();

    assertThat(testObject.isDown()).isFalse();
  }

  @Test
  public void keyIsDownForMultipleTicks_EvenWhenPressedOnce() {
    testObject.down();
    testObject.tick();
    testObject.tick();
    testObject.tick();

    assertThat(testObject.isDown()).isTrue();
  }

  @Test
  public void isClicked_ReturnsFalse() {
    assertThat(testObject.isClicked()).isFalse();
  }

  @Test
  public void isClicked_ReturnsTrue_WhenKeyClicked() {
    testObject.down();
    testObject.tick();

    assertThat(testObject.isClicked()).isTrue();
  }

  @Test
  public void isClicked_ReturnsFalseTwoTicks_AfterPress() {
    testObject.down();
    testObject.tick();
    testObject.tick();

    assertThat(testObject.isClicked()).isFalse();
  }

  @Test
  public void isClicked_ReturnsTrueForEachPress() {
    testObject.down();
    testObject.up();
    testObject.down();
    testObject.tick();
    testObject.tick();

    assertThat(testObject.isClicked()).isTrue();
  }

  @Test
  public void isClicked_ReturnsFalseAfterBeingHandled() {
    testObject.down();
    testObject.up();
    testObject.down();
    testObject.tick();
    testObject.tick();
    testObject.tick();

    assertThat(testObject.isClicked()).isFalse();
  }

}