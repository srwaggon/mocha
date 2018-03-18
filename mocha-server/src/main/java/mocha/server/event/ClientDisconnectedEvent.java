package mocha.server.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.server.ClientWorker;

@Data
@AllArgsConstructor
public class ClientDisconnectedEvent {
  private final ClientWorker clientWorker;
}
