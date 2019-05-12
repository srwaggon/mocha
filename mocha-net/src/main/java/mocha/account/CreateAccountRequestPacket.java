package mocha.account;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

import static mocha.net.packet.PacketType.CREATE_ACCOUNT_REQUEST;

public class CreateAccountRequestPacket extends AbstractPacket {

  public CreateAccountRequestPacket() {
  }

  public CreateAccountRequestPacket(String accountName, String emailAddress) {
    addToData(accountName);
    addToData(emailAddress);
  }

  @Override
  public PacketType getType() {
    return CREATE_ACCOUNT_REQUEST;
  }

  public String getAccountName() {
    return getData(1);
  }

  public String getEmailAddress() {
    return getData(2);
  }
}
