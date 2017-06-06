package mocha.client.main.gfx;

import org.springframework.stereotype.Component;

import javafx.scene.canvas.Canvas;

@Component
public class MochaCanvas extends Canvas {

  public MochaCanvas() {
    super(600, 400);
  }
}
