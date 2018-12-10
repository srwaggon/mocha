package mocha.game.world.chunk.tile.item;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StoneTest {

  private Stone testObject;
  @Before
  public void setUp() {
    testObject = new Stone();
  }

  @Test
  public void blocksMove_ReturnsTrue() {
    assertTrue(testObject.blocksMove());
  }
}