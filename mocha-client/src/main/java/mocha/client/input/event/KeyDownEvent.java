package mocha.client.input.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.client.input.GameKey;

@Data
@AllArgsConstructor
public class KeyDownEvent {
  private GameKey gameKey;
}
