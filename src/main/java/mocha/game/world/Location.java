package mocha.game.world;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mocha.game.world.chunk.Chunk;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Location {

  private double x;
  private double y;

  public static Location at(int x, int y) {
    return new Location(x, y);
  }

  public double getX() {
    return x;
  }

  public int getXAsInt() {
    return (int) x;
  }

  public int getYAsInt() {
    return (int) y;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void set(Location location) {
    this.x = location.getX();
    this.y = location.getY();
  }

  public Location addNew(double x, double y) {
    return new Location(this.getX() + x, this.getY() + y);
  }

  public void add(double x, double y) {
    addX(x);
    addY(y);
  }

  private void addX(double x) {
    this.x = this.getX() + x;
  }

  private void addY(double y) {
    this.y = this.getY() + y;
  }

  public void add(Location location) {
    this.addX(location.getX());
    this.addY(location.getY());
  }

  @Override
  public String toString() {
    return String.format("{\"x\": %f, \"y\": %f}", x, y);
  }

  public Location getChunkIndex() {
    return new Location(getChunkX(), getChunkY());
  }

  private int getChunkX() {
    return getX() < 0
        ? (int) ((getX() + 1) / Chunk.getWidth()) - 1
        : (int) (getX() / Chunk.getWidth());
  }

  private int getChunkY()  {
    return getY() < 0
        ? (int) ((getY() + 1) / Chunk.getHeight()) - 1
        : (int) (getY() / Chunk.getHeight());
  }
}
