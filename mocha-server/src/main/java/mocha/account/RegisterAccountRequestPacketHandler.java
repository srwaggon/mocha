package mocha.account;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.net.packet.PacketHandler;

@Component
public class RegisterAccountRequestPacketHandler implements PacketHandler<RegisterAccountRequestPacket> {

  private Logger logger = LoggerFactory.getLogger(RegisterAccountRequestPacketHandler.class);
  private AccountService accountService;

  @Inject
  public RegisterAccountRequestPacketHandler(
      AccountService accountService
  ) {
    this.accountService = accountService;
  }

  @Override
  @Subscribe
  public void handle(RegisterAccountRequestPacket packet) {
    try {
      registerAccount(packet);
    } catch (AccountNameTakenException e) {
      logger.error("Name taken: ", e);
    }
  }

  private void registerAccount(RegisterAccountRequestPacket packet) throws AccountNameTakenException {
    accountService.registerAccount(
        packet.getAccountName(),
        packet.getEmailAddress()
    );
  }
}
