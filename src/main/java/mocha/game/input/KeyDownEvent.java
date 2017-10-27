package mocha.game.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.game.InputKey;

@Data
@AllArgsConstructor
public class KeyDownEvent {
  private InputKey inputKey;
}
