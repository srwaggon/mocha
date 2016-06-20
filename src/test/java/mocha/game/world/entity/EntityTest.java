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
  public void getX_ReturnsXLocation() {
    assertEquals(0, testObject.getX(), 0);
  }

  @Test
  public void setX_changesXLocation() {
    double rand1 = Math.random() * 10;
    double rand2 = Math.random() * 10;

    testObject.setX(rand1);
    assertEquals(rand1, testObject.getX(), 0);

    testObject.setX(rand2);
    assertEquals(rand2, testObject.getX(), 0);
  }

  @Test
  public void getY_ReturnsXLocation() {
    assertEquals(0.0, testObject.getY(), 0);
  }

  @Test
  public void setY_changesYLocation() {
    double rand1 = Math.random() * 10;
    double rand2 = Math.random() * 10;

    testObject.setY(rand1);
    assertEquals(rand1, testObject.getY(), 0);

    testObject.setY(rand2);
    assertEquals(rand2, testObject.getY(), 0);
  }

  @Test
  public void getId_ReturnsId() {
    assertEquals(1, testObject.getId());
  }
}
