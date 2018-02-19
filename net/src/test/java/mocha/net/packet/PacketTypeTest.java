package mocha.net.packet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PacketTypeTest {

  @Test
  public void testResolve() {
    GlobalMessagePacket expectedPacket = new GlobalMessagePacket("yoyo");
    String expected = expectedPacket.construct();

    Packet packet = PacketType.resolve(expectedPacket);

    assertEquals(expected, packet.construct());
  }
}