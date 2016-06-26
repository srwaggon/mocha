package mocha.game;

import mocha.game.world.World;
import mocha.game.world.entity.PlayerMob;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

  @Mock
  private PlayerMob playerMob;
  private Game testObject;

  @Before
  public void setUp() throws Exception {
    testObject = new Game(new World(), playerMob);
  }

  @Test
  public void getWorld_ReturnsMappingOfMaps() {
    World world = testObject.getWorld();
    testObject.init();

    assertNotNull(world);
  }
}
