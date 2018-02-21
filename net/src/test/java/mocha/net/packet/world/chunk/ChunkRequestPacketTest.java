package mocha.net.packet.world.chunk;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.Location;
import mocha.net.packet.PacketType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChunkRequestPacketTest {

  private ChunkRequestPacket testObject;

  @Before
  public void setUp() {
    testObject = new ChunkRequestPacket(new Location(32.33, 64.77777));
  }

  @Test
  public void getType_ReturnsChunkPacketRequest() {
    assertThat(testObject.getType()).isEqualTo(PacketType.CHUNK_REQUEST);
  }

  @Test
  public void construct_ContainsTheChunkTypeAsTheZeroethPhrase() {
    String expected = PacketType.CHUNK_REQUEST.name();
    String actual = testObject.construct().split(PacketType.SEPARATOR)[0];
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void construct_ContainsTheXAsAnIntegerAsTheFirstPhrase() {
    String expected = "32";
    String actual = testObject.construct().split(PacketType.SEPARATOR)[1];
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void construct_ContainsTheYAsAnIntegerAsTheSecondPhrase() {
    String expected = "64";
    String actual = testObject.construct().split(PacketType.SEPARATOR)[2];
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void getDate_ReturnsTheChunkTypeAsTheZeroethPhrase() {
    String expected = PacketType.CHUNK_REQUEST.name();
    String actual = testObject.getData()[0];
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void build_TransfersTheFirstItemFromTheSuppliedArray() {
    String[] data = new String[3];
    String expected = "expected";
    data[1] = expected;
    testObject.build(data);

    assertThat(testObject.getData()[1]).isEqualTo(expected);
  }

  @Test
  public void build_TransfersTheSecondItemFromTheSuppliedArray() {
    String[] data = new String[3];
    String expected = "expected";
    data[2] = expected;
    testObject.build(data);

    assertThat(testObject.getData()[2]).isEqualTo(expected);
  }
}