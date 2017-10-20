package mocha.game.world.tile;

import org.junit.Test;

import mocha.gfx.Colors;

import static org.junit.Assert.*;

public class TileTypeTest {

  // region getSymbol
  @Test
  public void getSymbol_ReturnsDot_ForDirt() {
    assertEquals(".", TileType.DIRT.getSymbol());
  }

  @Test
  public void getColor_ReturnsBrown_ForDirt() {
    assertEquals(Colors.DIRT_BROWN, TileType.DIRT.getColor());
  }

  @Test
  public void getSymbol_Returnsw_ForGrass() {
    assertEquals("w", TileType.GRASS.getSymbol());
  }

  @Test
  public void getColor_ReturnsGreen_ForGrass() {
    assertEquals(Colors.GRASS_GREEN, TileType.GRASS.getColor());
  }

  @Test
  public void getSymbol_Returnso_ForStone() {
    assertEquals("o", TileType.STONE.getSymbol());
  }
  // endregion getSymbol

  // region getColor
  @Test
  public void getColor_ReturnsGrey_ForStone() {
    assertEquals(Colors.STONE_GREY, TileType.STONE.getColor());
  }
  // endregion getColor

  // region isBlocking


  @Test
  public void gridIsNotBlocking() {
    assertFalse(TileType.GRID.isBlocking());
  }

  @Test
  public void dirtIsNotBlocking() {
    assertFalse(TileType.DIRT.isBlocking());
  }

  @Test
  public void stoneIsBlocking() {
    assertTrue(TileType.STONE.isBlocking());
  }

  @Test
  public void grassIsNotBlocking() {
    assertFalse(TileType.GRASS.isBlocking());
  }

  @Test
  public void waterIsBlocking() {
    assertTrue(TileType.WATER.isBlocking());
  }
  // endregion isBlocking

}