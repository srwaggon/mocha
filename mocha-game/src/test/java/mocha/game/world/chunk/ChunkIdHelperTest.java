package mocha.game.world.chunk;

import org.junit.Test;

import mocha.game.world.Location;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ChunkIdHelperTest {

  @Test
  public void getRingDiameter() {
    assertThat(ChunkIdHelper.getRingDiameter(1)).isEqualTo(1);
    assertThat(ChunkIdHelper.getRingDiameter(2)).isEqualTo(3);
    assertThat(ChunkIdHelper.getRingDiameter(3)).isEqualTo(3);
    assertThat(ChunkIdHelper.getRingDiameter(4)).isEqualTo(3);
    assertThat(ChunkIdHelper.getRingDiameter(8)).isEqualTo(3);
    assertThat(ChunkIdHelper.getRingDiameter(9)).isEqualTo(3);
    assertThat(ChunkIdHelper.getRingDiameter(10)).isEqualTo(5);
    assertThat(ChunkIdHelper.getRingDiameter(11)).isEqualTo(5);
    assertThat(ChunkIdHelper.getRingDiameter(13)).isEqualTo(5);
    assertThat(ChunkIdHelper.getRingDiameter(25)).isEqualTo(5);
    assertThat(ChunkIdHelper.getRingDiameter(26)).isEqualTo(7);
  }

  @Test
  public void getRingBeginningId() {
    assertThat(ChunkIdHelper.getRingBeginningId(1)).isEqualTo(1);
    assertThat(ChunkIdHelper.getRingBeginningId(2)).isEqualTo(2);
    assertThat(ChunkIdHelper.getRingBeginningId(3)).isEqualTo(2);
    assertThat(ChunkIdHelper.getRingBeginningId(4)).isEqualTo(2);
    assertThat(ChunkIdHelper.getRingBeginningId(8)).isEqualTo(2);
    assertThat(ChunkIdHelper.getRingBeginningId(9)).isEqualTo(2);
    assertThat(ChunkIdHelper.getRingBeginningId(10)).isEqualTo(10);
    assertThat(ChunkIdHelper.getRingBeginningId(11)).isEqualTo(10);
    assertThat(ChunkIdHelper.getRingBeginningId(13)).isEqualTo(10);
    assertThat(ChunkIdHelper.getRingBeginningId(25)).isEqualTo(10);
    assertThat(ChunkIdHelper.getRingBeginningId(26)).isEqualTo(26);
  }

  @Test
  public void getChunkXIndex() {
    assertThat(ChunkIdHelper.getChunkXIndex(1)).isEqualTo(0);
    assertThat(ChunkIdHelper.getChunkXIndex(2)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkXIndex(3)).isEqualTo(0);
    assertThat(ChunkIdHelper.getChunkXIndex(4)).isEqualTo(1);
    assertThat(ChunkIdHelper.getChunkXIndex(5)).isEqualTo(1);
    assertThat(ChunkIdHelper.getChunkXIndex(6)).isEqualTo(1);
    assertThat(ChunkIdHelper.getChunkXIndex(7)).isEqualTo(0);
    assertThat(ChunkIdHelper.getChunkXIndex(8)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkXIndex(9)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkXIndex(10)).isEqualTo(-2);
    assertThat(ChunkIdHelper.getChunkXIndex(11)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkXIndex(13)).isEqualTo(1);
    assertThat(ChunkIdHelper.getChunkXIndex(14)).isEqualTo(2);
    assertThat(ChunkIdHelper.getChunkXIndex(17)).isEqualTo(2);
    assertThat(ChunkIdHelper.getChunkXIndex(21)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkXIndex(25)).isEqualTo(-2);
    assertThat(ChunkIdHelper.getChunkXIndex(32)).isEqualTo(3);
    assertThat(ChunkIdHelper.getChunkXIndex(33)).isEqualTo(3);
  }

  @Test
  public void getChunkYIndex() {
    assertThat(ChunkIdHelper.getChunkYIndex(1)).isEqualTo(0);
    assertThat(ChunkIdHelper.getChunkYIndex(2)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkYIndex(3)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkYIndex(4)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkYIndex(5)).isEqualTo(0);
    assertThat(ChunkIdHelper.getChunkYIndex(6)).isEqualTo(1);
    assertThat(ChunkIdHelper.getChunkYIndex(7)).isEqualTo(1);
    assertThat(ChunkIdHelper.getChunkYIndex(8)).isEqualTo(1);
    assertThat(ChunkIdHelper.getChunkYIndex(9)).isEqualTo(0);
    assertThat(ChunkIdHelper.getChunkYIndex(10)).isEqualTo(-2);
    assertThat(ChunkIdHelper.getChunkYIndex(11)).isEqualTo(-2);
    assertThat(ChunkIdHelper.getChunkYIndex(13)).isEqualTo(-2);
    assertThat(ChunkIdHelper.getChunkYIndex(14)).isEqualTo(-2);
    assertThat(ChunkIdHelper.getChunkYIndex(15)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkYIndex(17)).isEqualTo(1);
    assertThat(ChunkIdHelper.getChunkYIndex(21)).isEqualTo(2);
    assertThat(ChunkIdHelper.getChunkYIndex(25)).isEqualTo(-1);
    assertThat(ChunkIdHelper.getChunkYIndex(32)).isEqualTo(-3);
    assertThat(ChunkIdHelper.getChunkYIndex(33)).isEqualTo(-2);
  }

  @Test
  public void getLocationOfChunk() {
    assertThat(ChunkIdHelper.getLocationOfChunkById(1)).isEqualTo(new Location(0, 0));
    assertThat(ChunkIdHelper.getLocationOfChunkById(2)).isEqualTo(new Location(-512, -512));
    assertThat(ChunkIdHelper.getLocationOfChunkById(3)).isEqualTo(new Location(0, -512));
    assertThat(ChunkIdHelper.getLocationOfChunkById(4)).isEqualTo(new Location(512, -512));
    assertThat(ChunkIdHelper.getLocationOfChunkById(5)).isEqualTo(new Location(512, 0));
    assertThat(ChunkIdHelper.getLocationOfChunkById(6)).isEqualTo(new Location(512, 512));
    assertThat(ChunkIdHelper.getLocationOfChunkById(7)).isEqualTo(new Location(0, 512));
    assertThat(ChunkIdHelper.getLocationOfChunkById(8)).isEqualTo(new Location(-512, 512));
    assertThat(ChunkIdHelper.getLocationOfChunkById(9)).isEqualTo(new Location(-512, 0));
    assertThat(ChunkIdHelper.getLocationOfChunkById(10)).isEqualTo(new Location(-1024, -1024));
    assertThat(ChunkIdHelper.getLocationOfChunkById(11)).isEqualTo(new Location(-512, -1024));
    assertThat(ChunkIdHelper.getLocationOfChunkById(13)).isEqualTo(new Location(512, -1024));
    assertThat(ChunkIdHelper.getLocationOfChunkById(14)).isEqualTo(new Location(1024, -1024));
    assertThat(ChunkIdHelper.getLocationOfChunkById(15)).isEqualTo(new Location(1024, -512));
    assertThat(ChunkIdHelper.getLocationOfChunkById(17)).isEqualTo(new Location(1024, 512));
    assertThat(ChunkIdHelper.getLocationOfChunkById(21)).isEqualTo(new Location(-512, 1024));
    assertThat(ChunkIdHelper.getLocationOfChunkById(25)).isEqualTo(new Location(-1024, -512));
    assertThat(ChunkIdHelper.getLocationOfChunkById(32)).isEqualTo(new Location(1536, -1536));
    assertThat(ChunkIdHelper.getLocationOfChunkById(33)).isEqualTo(new Location(1536, -1024));
  }
}