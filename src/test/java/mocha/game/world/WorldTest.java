package mocha.game.world;

import org.junit.Before;
import org.junit.Test;

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
    testObject.addMap(new Map(expectedMapId, 0, 0));

    Map map = testObject.getMapById(expectedMapId);
    int actualMapId = map.getId();

    assertEquals(expectedMapId, actualMapId);
  }

  @Test
  public void getMapById_ReturnsMap_ByThatId_InductiveCase() {
    int expectedMapId0 = 0;
    testObject.addMap(new Map(expectedMapId0, 0, 0));
    int expectedMapId26 = 26;
    testObject.addMap(new Map(expectedMapId26, 0, 0));

    assertEquals(expectedMapId0, testObject.getMapById(expectedMapId0).getId());
    assertEquals(expectedMapId26, testObject.getMapById(expectedMapId26).getId());
  }
}