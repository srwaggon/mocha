package mocha.net.packet;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GlobalMessagePacketTest {

  @Test
  public void getCode_ReturnsGlobalMessageCode() {
    assertEquals(PacketType.GLOBAL_MESSAGE, new GlobalMessagePacket("test").getType());
  }

  @Test
  public void construct_ReturnsStringContainingMessage() {
    assertEquals("GLOBAL_MESSAGE test", new GlobalMessagePacket("test").construct());
  }

}