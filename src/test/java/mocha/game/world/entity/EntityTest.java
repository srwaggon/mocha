package mocha.game.world.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityTest {

  private Entity testObject;

  @Before
  public void setUp() {
    testObject = new Entity(1);
  }

  @Test
  public void getId_ReturnsId() {
    assertEquals(1, testObject.getId());
  }

  @Test
  public void tick_MovesTheEntityByX() {
    // TODO?
  }

  @Test
  public void tick_MovesTheEntityByY() {
    // TODO?
  }
}
