package mocha.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.net.packet.PacketHandler;

@Component
public class CreateAccountRequestPacketHandler implements PacketHandler<CreateAccountRequestPacket> {

  private Logger logger = LoggerFactory.getLogger(CreateAccountRequestPacketHandler.class);
  private AccountService accountService;

  @Inject
  public CreateAccountRequestPacketHandler(
      AccountService accountService
  ) {
    this.accountService = accountService;
  }

  @Override
  public void handle(CreateAccountRequestPacket packet) {
    try {
      createAccount(packet);
    } catch (AccountNameTakenException e) {
      logger.error("Name taken: ", e);
    }
  }

  private void createAccount(CreateAccountRequestPacket packet) throws AccountNameTakenException {
    accountService.createAccount(
        packet.getAccountName(),
        packet.getEmailAddress()
    );
  }
}
