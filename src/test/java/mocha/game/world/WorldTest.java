package mocha.game.world;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mocha.game.world.map.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldTest {

  private World testObject;

  @BeforeEach
  public void setUp() throws Exception {
    this.testObject = new World();
  }

  @Test
  public void getMapById_ReturnsMap_ByThatId() {
    int expectedMapId = 0;
    testObject.addMap(new Map(expectedMapId, 1, 1));

    Map map = testObject.getMapById(expectedMapId);
    int actualMapId = map.getId();

    assertEquals(expectedMapId, actualMapId);
  }

  @Test
  public void getMapById_ReturnsMap_ByThatId_InductiveCase() {
    int expectedMapId0 = 0;
    testObject.addMap(new Map(expectedMapId0, 1, 1));
    int expectedMapId26 = 26;
    testObject.addMap(new Map(expectedMapId26, 2, 2));

    assertEquals(expectedMapId0, testObject.getMapById(expectedMapId0).getId());
    assertEquals(expectedMapId26, testObject.getMapById(expectedMapId26).getId());
  }
}