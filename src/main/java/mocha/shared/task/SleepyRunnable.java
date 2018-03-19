package mocha.shared.task;

public interface SleepyRunnable extends Runnable {

  default void nap() {
    nap(10);
  }

  default void nap(int napTime) {
    try {
      Thread.sleep(napTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
