package mocha.server.event;

import org.springframework.stereotype.Component;

import mocha.account.AccountConnection;
import mocha.account.LoginSuccessEvent;
import mocha.game.player.Player;
import mocha.game.player.PlayerJoinedEvent;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.MochaConnection;

@Component
public class ServerEventBus extends NetworkedMochaEventBus {

  public void postPlayerJoinedEvent(MochaConnection mochaConnection, Player player) {
    this.post(new PlayerJoinedEvent(mochaConnection, player));
  }

  public void postLoginSuccess(AccountConnection accountConnection) {
    this.post(new LoginSuccessEvent(accountConnection));
  }
}
