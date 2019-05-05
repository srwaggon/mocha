package mocha.game.event;

public interface MochaEventHandler<T extends MochaEvent> {

  void handle(T t);
}
