package mocha.game.world.entity;

import org.junit.Before;
import org.junit.Test;

import mocha.game.world.Location;

import static org.junit.Assert.*;

public class LocationTest {

  private Location testObject;

  @Before
  public void setUp() throws Exception {
   testObject = new Location();
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
}
