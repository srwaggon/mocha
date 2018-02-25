package mocha.net.packet.world.chunk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.tile.TileFactory;
import mocha.game.world.tile.TileType;
import mocha.net.packet.PacketType;

import static org.junit.Assert.assertEquals;

public class ChunkPacketTest {

  private ChunkPacket testObject;

  private Chunk testChunk;

  @Before
  public void setUp() {
    ChunkFactory chunkFactory = ChunkFactory.builder()
        .tileFactory(new TileFactory())
        .build();
    testChunk = chunkFactory.newRandomDefault(0, 0);

    testObject = new ChunkPacket();
    String[] data = new String[2];
    data[1] = testChunk.buildTileData();
    testObject.build(data);
  }

  @Test
  public void getCode_ReturnsMapCode() {
    Assert.assertEquals(PacketType.CHUNK, testObject.getType());
  }

  @Test
  public void construct_ContainsMapCode_AsZeroethPhrase() {
    assertEquals(PacketType.CHUNK.name(), testObject.getData()[0]);
    assertEquals(PacketType.CHUNK.name(), getPhrase(0));
  }

  @Test
  public void construct_ContainsTileData_AsFirstPhrase() {
    int index = 1;

    StringBuilder tilesBuilder = new StringBuilder();
    for (TileType tile : testChunk.getTiles()) {
      tilesBuilder.append(tile.getSymbol());
    }

    assertEquals(tilesBuilder.toString(), getPhrase(index));
  }

  private String getPhrase(int index) {
    return testObject.construct().split(PacketType.SEPARATOR)[index];
  }

}