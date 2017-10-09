package mocha.game;

import mocha.game.world.World;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.PlayerMob;
import mocha.game.world.entity.movement.InputMomentumMovement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

  private Game testObject;
  @Mock
  private EntityFactory entityFactory;
  @Mock
  private PlayerMob playerMob;

  @Before
  public void setUp() throws Exception {
    when(entityFactory.createPlayer()).thenReturn(playerMob);
    testObject = new Game(new World(), entityFactory);
  }

  @Test
  public void getWorld_ReturnsMappingOfMaps() {
    World world = testObject.getWorld();
    testObject.init();

    assertNotNull(world);
  }
}
