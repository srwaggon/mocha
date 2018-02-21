package mocha.net;

class DisconnectedException extends Throwable {
  DisconnectedException(Exception source) {
    super("Connection disconnected.", source);
  }
}
