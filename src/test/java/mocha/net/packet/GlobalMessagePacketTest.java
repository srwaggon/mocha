package mocha.net.packet;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GlobalMessagePacketTest {

  @Test
  public void getCode_ReturnsGlobalMessageCode() {
    assertEquals(PacketCode.GLOBAL_MESSAGE, new GlobalMessagePacket("test").getCode());
  }

  @Test
  public void construct_ReturnsStringContainingMessage() {
    assertEquals("GLOBAL_MESSAGE test", new GlobalMessagePacket("test").construct());
  }

}