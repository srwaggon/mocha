package mocha.account;

import lombok.AllArgsConstructor;
import lombok.Value;
import mocha.game.event.MochaEvent;

@AllArgsConstructor
@Value
public class LoginSuccessEvent implements MochaEvent {

  private AccountConnection accountConnection;
}
