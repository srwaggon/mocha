package mocha.game.world;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mocha.game.world.chunk.Chunk;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Location {

  private int x;
  private int y;

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

  public void addX(int x) {
    this.x = this.x + x;
  }

  public void addY(int y) {
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
}
