package mocha.client.gfx.view;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.geometry.Rectangle2D;
import mocha.client.gfx.sprite.ScaleProvider;
import mocha.game.player.PlayerService;
import mocha.game.world.Location;

@Component
public class Camera {

  private DoubleProperty widthProperty = new DoublePropertyBase() {
    @Override
    public Object getBean() {
      return Camera.this;
    }

    @Override
    public String getName() {
      return "width";
    }
  };

  private DoubleProperty heightProperty = new DoublePropertyBase() {
    @Override
    public Object getBean() {
      return Camera.this;
    }

    @Override
    public String getName() {
      return "height";
    }
  };
  private PlayerService playerService;
  private ScaleProvider scaleProvider;

  @Inject
  public Camera(PlayerService playerService, ScaleProvider scaleProvider) {
    this.playerService = playerService;
    this.scaleProvider = scaleProvider;
    widthProperty.setValue(1024.0);
    heightProperty.setValue(768.0);
  }

  private Location getLocation() {
    return getPlayerLocation();
  }

  public Rectangle2D getBounds() {
    double width = getWidth();
    double height = getHeight();
    double x = getLocation().getX() - width / 2;
    double y = getLocation().getY() - height / 2;
    return new Rectangle2D(x, y, width, height);
  }

  private Location getPlayerLocation() {
    return playerService.getEntityForPlayer().getLocation();
  }

  private double getWidth() {
    return widthProperty.get() / scaleProvider.getScale();
  }

  private double getHeight() {
    return heightProperty.get() / scaleProvider.getScale();
  }

  DoubleProperty widthProperty() {
    return widthProperty;
  }

  DoubleProperty heightProperty() {
    return heightProperty;
  }
}
