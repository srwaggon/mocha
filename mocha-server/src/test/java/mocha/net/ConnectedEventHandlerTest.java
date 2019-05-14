package mocha.net;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import mocha.net.event.ConnectedEvent;
import mocha.net.exception.DisconnectedException;
import mocha.net.packet.MochaConnection;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConnectedEventHandlerTest {

  @Mock
  private MochaConnection mochaConnection;

  @Inject
  private ConnectedEventHandler connectedEventHandler;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @NotNull
  private ConnectedEvent newConnectedEvent() {
    return new ConnectedEvent(mochaConnection);
  }

  @Test
  public void handle_disconnects_WhenThePacketTypeIsNotLogin() throws DisconnectedException {
    LoginSuccessPacket badPacket = new LoginSuccessPacket(0);
    when(mochaConnection.readPacket()).thenReturn(badPacket);

    connectedEventHandler.handle(newConnectedEvent());

    verify(mochaConnection).disconnect();
  }

  @Test
  public void handle_WhenLoginRequestPacket() {

  }
}