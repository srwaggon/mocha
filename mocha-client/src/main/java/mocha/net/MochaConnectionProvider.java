package mocha.net;

import org.springframework.stereotype.Component;

import mocha.net.packet.MochaConnection;

@Component
public class MochaConnectionProvider {

  private MochaConnection mochaConnection;

  public MochaConnection get() {
    return mochaConnection;
  }

  public void set(MochaConnection mochaConnection) {
    this.mochaConnection = mochaConnection;
  }
}
