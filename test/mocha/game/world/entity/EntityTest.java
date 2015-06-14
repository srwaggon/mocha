package mocha.game.world.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {

  private Entity testObject;

  @Before
  public void setUp() {
    testObject = new Entity(1);
  }

  @Test
  public void getX_ReturnsXLocation() {
    assertEquals(0, testObject.getX());
  }

  @Test
  public void setX_changesXLocation() {
    int rand1 = (int) (Math.random() * 10);
    int rand2 = (int) (Math.random() * 10);

    testObject.setX(rand1);
    assertEquals(rand1, testObject.getX());

    testObject.setX(rand2);
    assertEquals(rand2, testObject.getX());
  }

  @Test
  public void getY_ReturnsXLocation() {
    assertEquals(0, testObject.getY());
  }

  @Test
  public void setY_changesYLocation() {
    int rand1 = (int) (Math.random() * 10);
    int rand2 = (int) (Math.random() * 10);

    testObject.setY(rand1);
    assertEquals(rand1, testObject.getY());

    testObject.setY(rand2);
    assertEquals(rand2, testObject.getY());
  }

  @Test
  public void getId_ReturnsId() {
    assertEquals(1, testObject.getId());
    assertEquals(10, new Entity(10).getId());
  }

}
