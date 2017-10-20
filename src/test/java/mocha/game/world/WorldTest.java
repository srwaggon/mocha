package mocha.game.world;


import org.junit.Before;
import org.junit.Test;

import mocha.game.world.map.Map;

import static org.junit.Assert.assertEquals;

public class WorldTest {

  private World testObject;

  @Before
  public void setUp() throws Exception {
    this.testObject = new World();
  }

  @Test
  public void getMapById_ReturnsMap_ByThatId() {
    int expectedMapId = 0;
    Map newMap = Map.builder().id(expectedMapId).build();
    testObject.addMap(newMap);

    Map map = testObject.getMapById(expectedMapId);
    int actualMapId = map.getId();

    assertEquals(expectedMapId, actualMapId);
  }

  @Test
  public void getMapById_ReturnsMap_ByThatId_InductiveCase() {
    int expectedMapId0 = 0;
    testObject.addMap(Map.builder().id(expectedMapId0).build());
    int expectedMapId26 = 26;
    testObject.addMap(Map.builder().id(expectedMapId26).build());

    assertEquals(expectedMapId0, testObject.getMapById(expectedMapId0).getId());
    assertEquals(expectedMapId26, testObject.getMapById(expectedMapId26).getId());
  }
}