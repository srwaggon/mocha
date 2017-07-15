package mocha.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

@Component
public class MochaRoot extends Group {

  private final Canvas canvas;

  @Inject
  public MochaRoot(Canvas canvas, Node... children) {
    super(children);
    this.canvas = canvas;
  }
}
