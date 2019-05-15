package mocha.account;

import lombok.AllArgsConstructor;
import lombok.Value;
import mocha.net.packet.MochaConnection;

@AllArgsConstructor
@Value
public class AccountConnection {

  private MochaConnection mochaConnection;
  private Account account;

}
