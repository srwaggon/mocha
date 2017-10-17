package mocha.game;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

  private Game testObject;
  @Mock
  private EntityFactory entityFactory;
  @Mock
  private Entity playerEntity;

  @Before
  public void setUp() throws Exception {
    when(entityFactory.createPlayer()).thenReturn(playerEntity);
    testObject = new Game(new World(), entityFactory);
  }

  @Test
  public void getWorld_ReturnsMappingOfMaps() {
    World world = testObject.getWorld();
    testObject.init();

    assertNotNull(world);
  }
}
