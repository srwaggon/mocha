package mocha.game.world;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LocationTest {

  private Location testObject = new Location();

  @Test
  public void equals_ReturnsTrueWhenTheXsAndYsMatch() {
    int x = 40;
    int y = 694684688;
    Location other = new Location(x, y);
    testObject.setX(x);
    testObject.setY(y);

    assertTrue(testObject.equals(other));
  }

  @Test
  public void equals_ReturnsFalseWhenTheXsDoNotMatch() {
    int x = 40;
    int y = 69468468;
    Location other = new Location(x, y);
    testObject.setX(x + 1);
    testObject.setY(y);

    assertFalse(testObject.equals(other));
  }

  @Test
  public void equals_ReturnsFalseWhenTheYsDoNotMatch() {
    int x = 40;
    int y = 69468468;
    Location other = new Location(x, y);
    testObject.setX(x);
    testObject.setY(y + 789416816);

    assertFalse(testObject.equals(other));
  }
}