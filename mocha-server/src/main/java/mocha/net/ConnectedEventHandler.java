package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import mocha.account.AccountService;
import mocha.account.CreateAccountRequestPacket;
import mocha.account.CreateAccountRequestPacketHandler;
import mocha.game.LoginRequestPacket;
import mocha.game.event.MochaEventHandler;
import mocha.net.event.ConnectedEvent;
import mocha.net.exception.DisconnectedException;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketType;
import mocha.server.event.ServerEventBus;

import static mocha.net.packet.PacketType.CREATE_ACCOUNT_REQUEST;
import static mocha.net.packet.PacketType.LOGIN_REQUEST;

@Component
public class ConnectedEventHandler implements MochaEventHandler<ConnectedEvent> {

  private Logger log = LoggerFactory.getLogger(ConnectedEventHandler.class);

  private ServerEventBus serverEventBus;
  private AccountService accountService;
  private CreateAccountRequestPacketHandler createAccountRequestPacketHandler;

  @Inject
  public ConnectedEventHandler(
      ServerEventBus serverEventBus,
      AccountService accountService,
      CreateAccountRequestPacketHandler createAccountRequestPacketHandler
  ) {
    this.serverEventBus = serverEventBus;
    this.accountService = accountService;
    this.createAccountRequestPacketHandler = createAccountRequestPacketHandler;
  }

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    log.info(connectedEvent.toString());

    MochaConnection playerConnection = connectedEvent.getMochaConnection();

    AtomicBoolean looping = new AtomicBoolean(true);
    while (looping.get()) {
      getPacket(playerConnection).ifPresent(packet -> {
        PacketType type = packet.getType();
        String data = packet.getData();

        if (CREATE_ACCOUNT_REQUEST.equals(type)) {
          handleCreateAccount(data);
        } else if (LOGIN_REQUEST.equals(type)) {
          handleLoginRequest(playerConnection, data);
          looping.set(false);
        } else {
          playerConnection.disconnect();
          looping.set(false);
        }
      });
    }
  }

  private void handleCreateAccount(String data) {
    CreateAccountRequestPacket createAccountRequestPacket = new CreateAccountRequestPacket();
    createAccountRequestPacket.build(data);
    createAccountRequestPacketHandler.handle(createAccountRequestPacket);
  }

  private void handleLoginRequest(MochaConnection playerConnection, String data) {
    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
    loginRequestPacket.build(data);

    accountService.login(playerConnection, loginRequestPacket)
        .ifPresent(serverEventBus::postLoginSuccess);
  }

  private Optional<Packet> getPacket(MochaConnection playerConnection) {
    try {
      return Optional.of(playerConnection.readPacket());
    } catch (DisconnectedException disconnectedException) {
      log.error("", disconnectedException);
    }
    return Optional.empty();
  }

}