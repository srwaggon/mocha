package mocha.game.world;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {

  // region NORTH
  @Test
  public void NORTH_getXMultiplier_Returns0() {
    assertEquals(0, Direction.NORTH.getXMultiplier());
  }

  @Test
  public void NORTH_getYMultiplier_ReturnsNegative1() {
    assertEquals(-1, Direction.NORTH.getYMultiplier());
  }
  // endregion NORTH

  // region EAST
  @Test
  public void EAST_getXMultiplier_Returns1() {
    assertEquals(1, Direction.EAST.getXMultiplier());
  }

  @Test
  public void EAST_getYMultipier_Returns0() {
    assertEquals(0, Direction.EAST.getYMultiplier());
  }
  // endregion EAST

  // region SOUTH
  @Test
  public void SOUTH_getXMultiplier_Returns0() {
    assertEquals(0, Direction.SOUTH.getXMultiplier());
  }

  @Test
  public void SOUTH_getYMultipier_Returns1() {
    assertEquals(1, Direction.SOUTH.getYMultiplier());
  }
  // endregion SOUTH

  // region WEST
  @Test
  public void WEST_getXMultiplier_ReturnsNegative1() {
    assertEquals(-1, Direction.WEST.getXMultiplier());
  }

  @Test
  public void WEST_getYMultipier_Returns1() {
    assertEquals(0, Direction.WEST.getYMultiplier());
  }
  // endregion WEST
}