package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.player.LocalPlayer;
import mocha.game.player.PlayerService;
import mocha.net.packet.PacketHandler;

@Component
public class LoginSuccessPacketHandler implements PacketHandler {

  private PlayerService playerService;

  @Inject
  public LoginSuccessPacketHandler(PlayerService playerService) {
    this.playerService = playerService;
  }

  @Subscribe
  public void handle(LoginSuccessPacket loginSuccessPacket) {
    playerService.addPlayer(new LocalPlayer(loginSuccessPacket.getPlayerId()));
  }
}