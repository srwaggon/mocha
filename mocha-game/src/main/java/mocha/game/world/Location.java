package mocha.game.world;

import java.util.Objects;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.tile.TileType;

public class Location {

  private int x;
  private int y;

  public Location() {
  }

  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Location(double x, double y) {
    this.x = (int) x;
    this.y = (int) y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void set(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Location at(int x, int y) {
    return new Location(x, y);
  }

  public void set(Location location) {
    this.x = location.getX();
    this.y = location.getY();
  }

  public Location addNew(int x, int y) {
    return new Location(this.getX() + x, this.getY() + y);
  }

  public void add(int x, int y) {
    addX(x);
    addY(y);
  }

  private void addX(int x) {
    this.x = this.x + x;
  }

  private void addY(int y) {
    this.y = this.y + y;
  }

  public void add(Location location) {
    this.addX(location.getX());
    this.addY(location.getY());
  }

  @Override
  public String toString() {
    return String.format("{\"x\": %d, \"y\": %d}", x, y);
  }

  public Location getChunkIndex() {
    return new Location(getChunkX(), getChunkY());
  }

  private int getChunkX() {
    return x < 0
        ? (x + 1) / Chunk.getWidth() - 1
        : x / Chunk.getWidth();
  }

  private int getChunkY() {
    return y < 0
        ? (y + 1) / Chunk.getHeight() - 1
        : y / Chunk.getHeight();
  }

  public Location copy() {
    return new Location(x, y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location location = (Location) o;
    return x == location.x &&
        y == location.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  public Location north() {
    return from(Direction.NORTH);
  }

  public Location east() {
    return from(Direction.EAST);
  }

  public Location south() {
    return from(Direction.SOUTH);
  }

  public Location west() {
    return from(Direction.WEST);
  }

  public Location from(Direction direction) {
    return addNew(direction.getXMultiplier() * TileType.SIZE, direction.getYMultiplier() * TileType.SIZE);
  }
}
