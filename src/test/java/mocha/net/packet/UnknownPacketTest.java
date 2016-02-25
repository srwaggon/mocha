package mocha.net.packet;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;


public class UnknownPacketTest {

  private UnknownPacket testObject;
  private GlobalMessagePacket packet = new GlobalMessagePacket("Unknown");

  @Before
  public void setUp() {
    testObject = new UnknownPacket(packet.construct());
  }

  @Test
  public void testBuild() throws Exception {

  }

  @Test
  public void getCode_ReturnsIdForData() throws Exception {
    assertEquals(PacketCode.GLOBAL_MESSAGE, testObject.getCode());
  }

  @Test
  public void getData_ReturnsDataForUnderlyingPacket() throws Exception {
    assertEquals(Arrays.toString(packet.getData()), Arrays.toString(testObject.getData()));
  }

  @Test
  public void construct_BuildsMessageForUnderlyingPacket() throws Exception {
    assertEquals(packet.construct(), testObject.construct());
  }

}