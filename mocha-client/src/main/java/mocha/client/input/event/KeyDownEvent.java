package mocha.client.input.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.client.input.InputKey;

@Data
@AllArgsConstructor
public class KeyDownEvent {
  private InputKey inputKey;
}
