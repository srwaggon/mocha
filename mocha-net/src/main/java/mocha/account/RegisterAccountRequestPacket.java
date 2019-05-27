package mocha.account;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

import static mocha.net.packet.PacketType.REGISTER_ACCOUNT_REQUEST;

public class RegisterAccountRequestPacket extends AbstractPacket {

  public RegisterAccountRequestPacket() {
  }

  public RegisterAccountRequestPacket(String accountName, String emailAddress) {
    addToData(accountName);
    addToData(emailAddress);
  }

  @Override
  public PacketType getType() {
    return REGISTER_ACCOUNT_REQUEST;
  }

  public String getAccountName() {
    return getData(1);
  }

  public String getEmailAddress() {
    return getData(2);
  }
}
