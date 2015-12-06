package mocha.game;

import mocha.game.world.World;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

  private Game testObject = new Game();

  @Test public void getWorld_ReturnsMappingOfMaps() {
    World world = testObject.getWorld();

    assertNotNull(world);
  }
}
