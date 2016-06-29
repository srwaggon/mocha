package mocha.game.world.entity;

import org.junit.Before;
import org.junit.Test;

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

  @Test
  public void setDx_DoesNotUpdateX() {
    double x = testObject.getX();

    testObject.setDx(1.0);

    assertEquals(x, testObject.getX(), 0);
  }

  @Test
  public void setDy_DoesNotUpdateY() {
    double y = testObject.getY();

    testObject.setDy(1.0);

    assertEquals(y, testObject.getY(), 0);
  }

  @Test
  public void applyDx_UpdatesXByDifferenceInX() throws Exception {
    double x = testObject.getX();
    double dx = 1.0;
    testObject.setDx(dx);

    testObject.applyDx();

    assertEquals(x + dx, testObject.getX(), 0.0);
  }

  @Test
  public void applyDy_UpdatesYByDifferenceInY() throws Exception {
    double y = testObject.getY();
    double dy = 1.0;
    testObject.setDy(dy);

    testObject.applyDy();

    assertEquals(y + dy, testObject.getY(), 0.0);
  }

  @Test
  public void applyDx_ResetsDxAfterMoving() {
    testObject.setDx(1.0);

    testObject.applyDx();

    assertEquals(0.0, testObject.getDx(), 0.0);
  }

  @Test
  public void applyDy_ResetsDyAfterMoving() {
    testObject.setDy(1.0);

    testObject.applyDy();

    assertEquals(0.0, testObject.getDy(), 0.0);
  }
}
