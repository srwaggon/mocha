package mocha.game.world;

public enum Direction {
  NORTH(0, -1),
  EAST(1, 0),
  SOUTH(0, 1),
  WEST(-1, 0)
  ;

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
}
