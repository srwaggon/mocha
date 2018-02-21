package mocha.client.input;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class KeyDownEvent {
  private InputKey inputKey;
}
