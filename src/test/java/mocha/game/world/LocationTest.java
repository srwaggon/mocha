package mocha.game.world;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LocationTest {

  private Location testObject = new Location();

  @Test
  public void equals_ReturnsTrueWhenTheXsAndYsMatch() throws Exception {
    double x = 40.0;
    double y = 69468468468.18949894;
    Location other = new Location(x, y);
    testObject.setX(x);
    testObject.setY(y);

    assertTrue(testObject.equals(other));
  }

  @Test
  public void equals_ReturnsFalseWhenTheXsDoNotMatch() throws Exception {
    double x = 40.0;
    double y = 69468468468.18949894;
    Location other = new Location(x, y);
    testObject.setX(x + 1);
    testObject.setY(y);

    assertFalse(testObject.equals(other));
  }

  @Test
  public void equals_ReturnsFalseWhenTheYsDoNotMatch() throws Exception {
    double x = 40.0;
    double y = 69468468468.18949894;
    Location other = new Location(x, y);
    testObject.setX(x);
    testObject.setY(y + 789416816345.0);

    assertFalse(testObject.equals(other));
  }
}