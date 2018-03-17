package mocha.net;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.NoSuchElementException;

import mocha.net.packet.Packet;
import mocha.net.packet.message.GlobalMessagePacket;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PacketConnectionTest {

  private PacketConnection testObject;

  @Mock
  private Connection connection;

  @Before
  public void setUp() {
    testObject = new PacketConnection(connection);
  }

  @Test
  public void hasPacket_returnFalse_WhenNoNextPacket() {
    assertFalse(testObject.hasPacket());
  }

  @Test
  public void hasPacket_ReturnsTrue_WhenPacketAvailable() {
    when(connection.hasLine()).thenReturn(true);

    assertTrue(testObject.hasPacket());
  }

  @Test
  public void sendPacket_PassesPacketToConnection() {
    testObject.sendPacket(new GlobalMessagePacket("Eyyy"));

    verify(connection).send(eq("GLOBAL_MESSAGE Eyyy"));
  }

  @Test
  public void readPacket_ReadsAvailablePacket() throws DisconnectedException {
    GlobalMessagePacket testPacket = new GlobalMessagePacket("eyyyy");
    when(connection.readLine()).thenReturn(testPacket.construct());

    Packet packet = testObject.readPacket();

    assertEquals(testPacket.construct(), packet.construct());
  }

  @Test
  public void readPacket_ThrowsDisconnectedException_WhenTheOtherSideHasDisconnected() throws DisconnectedException {
    when(connection.readLine()).thenThrow(new DisconnectedException(new NoSuchElementException()));

    assertThatExceptionOfType(DisconnectedException.class)
        .isThrownBy(() -> testObject.readPacket());
  }
}