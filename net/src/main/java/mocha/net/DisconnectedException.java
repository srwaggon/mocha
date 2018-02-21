package mocha.net;

public class DisconnectedException extends Throwable {
  DisconnectedException(Exception source) {
    super("Connection disconnected.", source);
  }
}
