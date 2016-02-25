package mocha.net.packet;

import org.junit.Test;

import static org.junit.Assert.*;

public class PacketCodeTest {

  @Test
  public void testResolve() throws Exception {
    GlobalMessagePacket expectedPacket = new GlobalMessagePacket("yoyo");
    String expected = expectedPacket.construct();

    Packet packet = PacketCode.resolve(expectedPacket);

    assertEquals(expected, packet.construct());
  }
}