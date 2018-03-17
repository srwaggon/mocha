package mocha.net.exception;

public class DisconnectedException extends Throwable {
  public DisconnectedException(Exception source) {
    super("Connection disconnected.", source);
  }
}
