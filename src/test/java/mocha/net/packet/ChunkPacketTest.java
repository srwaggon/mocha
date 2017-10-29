package mocha.net.packet;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.tile.TileFactory;
import mocha.game.world.tile.TileType;

import static org.junit.Assert.assertEquals;

public class ChunkPacketTest {

  private ChunkPacket subject;

  private Chunk testChunk;

  @Before
  public void setUp() {
    ChunkFactory chunkFactory = ChunkFactory.builder()
        .tileFactory(new TileFactory())
        .build();
    testChunk = chunkFactory.newRandomDefault(0, 0);

    subject = new ChunkPacket(testChunk);
  }

  @Test
  public void getCode_ReturnsMapCode() {
    assertEquals(PacketType.MAP, subject.getType());
  }

  @Test
  public void construct_ContainsMapCode_AsZeroethPhrase() {
    assertEquals(PacketType.MAP.name(), subject.getData()[0]);
    assertEquals(PacketType.MAP.name(), getPhrase(0));
  }

  @Test
  public void construct_ContainsColumnCount_AsSecondPhrase() {
    int index = 2;

    assertEquals(Chunk.SIZE, Integer.parseInt(subject.getData()[index]));
    assertEquals(Chunk.SIZE, getPhraseAsInt(index));
  }

  @Test
  public void construct_ContainsRowCount_AsThirdPhrase() {
    int index = 3;

    assertEquals(Chunk.SIZE, Integer.parseInt(subject.getData()[index]));
    assertEquals(Chunk.SIZE, getPhraseAsInt(index));
  }

  @Test
  public void construct_ContainsTileData_AsFourthPhrase() {
    int index = 4;

    StringBuilder tilesBuilder = new StringBuilder();
    for (TileType[] row : testChunk.getTiles()) {
      for (TileType tile : row) {
        tilesBuilder.append(tile.getSymbol());
      }
    }

    assertEquals(tilesBuilder.toString(), getPhrase(index));
  }

  private String getPhrase(int index) {
    return subject.construct().split(PacketType.SEPARATOR)[index];
  }

  private int getPhraseAsInt(int index) {
    return Integer.parseInt(getPhrase(index));
  }


}