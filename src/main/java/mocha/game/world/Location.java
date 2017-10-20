package mocha.game.world;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

  private double x;
  private double y;

  public double getX() {
    return x;
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

  public void addX(double x) {
    this.x += x;
  }

  public void addY(double y) {
    this.y += y;
  }

  @Override
  public String toString() {
    return "{" +
      "  \"x\": " + x + "," +
      "  \"y\": " + y + "" +
      "}";
  }

  @Override
  public boolean equals(Object myObject) {
    Location thisLocation = (Location) myObject;
    return thisLocation.getX() == this.getX() && thisLocation.getY() == this.getY();
  }
}
