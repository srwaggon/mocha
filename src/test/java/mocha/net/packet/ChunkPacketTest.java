package mocha.net.packet;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.tile.Tile;

import static org.junit.Assert.assertEquals;

public class ChunkPacketTest {

  private ChunkPacket subject;

  private Chunk testChunk;

  @Before
  public void setUp() {
    testChunk = new ChunkFactory().newRandomDefault(12, 11);
    testChunk.setId(10);

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
  public void construct_ContainsMapId_AsFirstPhrase() {
    int index = 1;

    assertEquals(testChunk.getId(), Integer.parseInt(subject.getData()[index]));
    assertEquals(testChunk.getId(), getPhraseAsInt(index));
  }

  @Test
  public void construct_ContainsColumnCount_AsSecondPhrase() {
    int index = 2;

    assertEquals(testChunk.getColumnCount(), Integer.parseInt(subject.getData()[index]));
    assertEquals(testChunk.getColumnCount(), getPhraseAsInt(index));
  }

  @Test
  public void construct_ContainsRowCount_AsThirdPhrase() {
    int index = 3;

    assertEquals(testChunk.getRowCount(), Integer.parseInt(subject.getData()[index]));
    assertEquals(testChunk.getRowCount(), getPhraseAsInt(index));
  }

  @Test
  public void construct_ContainsTileData_AsFourthPhrase() {
    int index = 4;

    StringBuilder tilesBuilder = new StringBuilder();
    for (Tile[] row : testChunk.getTiles()) {
      for (Tile tile : row) {
        tilesBuilder.append(tile.getTileType().getSymbol());
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