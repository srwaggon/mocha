package mocha.net.packet;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PacketTypeTest {

  @Test
  public void testResolve() {
    GlobalMessagePacket expectedPacket = new GlobalMessagePacket("yoyo");
    PacketType expected = PacketType.GLOBAL_MESSAGE;

    Packet packet = PacketType.resolve(expectedPacket);

    assertThat(packet.getType()).isEqualTo(expected);
  }
}