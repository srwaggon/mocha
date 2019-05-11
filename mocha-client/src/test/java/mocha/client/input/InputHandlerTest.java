package mocha.client.input;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mocha.client.event.ClientEventBus;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InputHandlerTest {

  @Mock
  private ClientEventBus eventBus;
  private InputHandler testObject;

  @Before
  public void setUp() {
    testObject = new InputHandler(eventBus);

    testObject.init();
  }

  @Test
  public void AKeyDownEventIsPublishedToTheEventBus_WhenTheEventHandlerFiresAKeyDownEvent() {
    KeyEvent keyEvent = newKeyEvent(KeyCode.ENTER);

    testObject.getKeyPressedHandler().handle(keyEvent);

    verify(eventBus).keyDown(GameKey.ACTION1);
  }

  private KeyEvent newKeyEvent(KeyCode keyCode) {
    return new KeyEvent(null, null, null, keyCode, false, false, false, false);
  }

}