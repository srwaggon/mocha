package mocha.server.event;

import mocha.net.event.NetworkedMochaEventBus;
import mocha.server.ClientWorker;

public class ServerEventBus extends NetworkedMochaEventBus {

  public void postClientConnectedEvent(ClientWorker clientWorker) {
    this.post(new ClientConnectedEvent(clientWorker));
  }
}
