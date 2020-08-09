package mocha.game.world;

import java.util.Arrays;

public enum Direction {
  NORTH(0, -1),
  EAST(1, 0),
  SOUTH(0, 1),
  WEST(-1, 0);

  Direction(int xMultiplier, int yMultiplier) {
    this.xMultiplier = xMultiplier;
    this.yMultiplier = yMultiplier;
  }

  private int xMultiplier;
  private int yMultiplier;

  public int getXMultiplier() {
    return xMultiplier;
  }

  public int getYMultiplier() {
    return yMultiplier;
  }

  public static int count() {
    return values().length;
  }

  public static Direction random() {
    return values()[(int) (count() * Math.random())];
  }

  public boolean isOpposite(Direction direction) {
    return this.opposite().equals(direction);
  }

  private Direction opposite() {
    return values()[(ordinal() + 2) % Direction.count()];
  }

  public static Direction findBest(int xDiff, int yDiff) {
    boolean xPreferred = Math.abs(xDiff) > Math.abs(yDiff);
    int xMultiplier = (xPreferred ? 1 : 0) * getMultiplier(xDiff);
    int yMultiplier = (xPreferred ? 0 : 1) * getMultiplier(yDiff);

    return findBestByMultiplier(xMultiplier, yMultiplier);
  }

  public static Direction findBestByMultiplier(int xMultiplier, int yMultiplier) {
    return Arrays.stream(values())
        .filter(direction -> direction.getXMultiplier() == xMultiplier && direction.getYMultiplier() == yMultiplier)
        .findFirst()
        .orElse(Direction.NORTH);
  }

  private static int getMultiplier(int diff) {
    return diff == 0 ? 0 : diff / Math.abs(diff);
  }
}
