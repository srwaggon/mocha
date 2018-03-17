package mocha.net.packet;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import mocha.net.packet.message.GlobalMessagePacket;

import static junit.framework.TestCase.assertEquals;


public class UnknownPacketTest {

  private UnknownPacket testObject;
  private GlobalMessagePacket packet = new GlobalMessagePacket("Unknown");

  @Before
  public void setUp() {
    testObject = new UnknownPacket(packet.construct());
  }

  @Test
  public void testBuild() {

  }

  @Test
  public void getCode_ReturnsIdForData() {
    assertEquals(PacketType.GLOBAL_MESSAGE, testObject.getType());
  }

  @Test
  public void getData_ReturnsDataForUnderlyingPacket() {
    assertEquals(Arrays.toString(packet.getData()), Arrays.toString(testObject.getData()));
  }

  @Test
  public void construct_BuildsMessageForUnderlyingPacket() {
    assertEquals(packet.construct(), testObject.construct());
  }

}