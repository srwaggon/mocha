package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Data;

@Data
@Component
public class WindowDimensions {

  private DoubleProperty widthProperty = new SimpleDoubleProperty(512);
  private DoubleProperty heightProperty = new SimpleDoubleProperty(384);

}
