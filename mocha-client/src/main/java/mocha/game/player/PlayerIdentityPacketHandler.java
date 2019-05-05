package mocha.game.player;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.PlayerIdentityPacket;
import mocha.net.packet.PacketHandler;

@Component
public class PlayerIdentityPacketHandler implements PacketHandler<PlayerIdentityPacket> {

  @Inject
  public PlayerIdentityPacketHandler() {
  }

  @Subscribe
  public void handle(PlayerIdentityPacket playerIdentityPacket) {

  }
}