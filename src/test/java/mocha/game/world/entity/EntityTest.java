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
    Location location = new Location();
    double x = location.getX();
    double dx = 1.0;
    location.setDx(dx);
    testObject.setLocation(location);

    testObject.tick();

    assertEquals(x + dx, location.getX(), 0.0);
  }

  @Test
  public void tick_MovesTheEntityByY() {
    Location location = new Location();
    double y = location.getY();
    double dy = 2.0;
    location.setDy(dy);
    testObject.setLocation(location);

    testObject.tick();

    assertEquals(y + dy, location.getY(), 0.0);
  }
}
