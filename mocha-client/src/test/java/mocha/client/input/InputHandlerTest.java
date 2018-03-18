package mocha.client.input;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mocha.client.event.ClientEventBus;
import mocha.client.input.event.KeyDownEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InputHandlerTest {

  @Mock
  private ClientEventBus eventBus;
  @Captor
  private ArgumentCaptor<KeyDownEvent> keyDownEventCaptor;
  private InputHandler testObject;

  @Before
  public void setUp() {
    testObject = InputHandler.builder()
        .eventBus(eventBus)
        .build();

    testObject.init();
  }

  @Test
  public void AKeyDownEventIsPublishedToTheEventBus_WhenTheEventHandlerFiresAKeyDownEvent() {
    KeyEvent keyEvent = newKeyEvent(KeyCode.ENTER);

    testObject.getKeyPressedHandler().handle(keyEvent);

    verify(eventBus).post(keyDownEventCaptor.capture());
    assertThat(keyDownEventCaptor.getValue().getGameKey()).isEqualTo(GameKey.PICKUP);
  }

  private KeyEvent newKeyEvent(KeyCode keyCode) {
    return new KeyEvent(null, null, null, keyCode, false, false, false, false);
  }

}