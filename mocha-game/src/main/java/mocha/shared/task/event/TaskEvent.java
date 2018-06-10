package mocha.shared.task.event;

public class TaskEvent {
  private final Runnable runnable;

  public TaskEvent(Runnable runnable) {
    this.runnable = runnable;
  }

  public Runnable getRunnable() {
    return runnable;
  }
}
