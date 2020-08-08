package mocha.game.world;

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
}
