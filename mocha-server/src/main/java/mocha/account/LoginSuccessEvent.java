package mocha.account;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;
import mocha.game.event.MochaEvent;

@AllArgsConstructor
@Value
@ToString
public class LoginSuccessEvent implements MochaEvent {

  private AccountConnection accountConnection;
}
