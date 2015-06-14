package mocha.game;

import mocha.game.world.Map;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

  private Game testObject = new Game();

  @Test public void getWorld_ReturnsMappingOfMaps() {
    Map expected = new Map(1, 0, 0);
    testObject.put(expected);

    Map actual = testObject.getWorld().get(expected.getId());

    assertSame(expected, actual);
  }
}
