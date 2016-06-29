package mocha.game.world.tile.item;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StoneTest {

  private Stone testObject;
  @Before
  public void setUp() throws Exception {
    testObject = new Stone();
  }

  @Test
  public void blocksMove_ReturnsTrue() throws Exception {
    assertTrue(testObject.blocksMove());
  }
}