package mocha.game;

public class CallCounter {

  private final String description;

  private long now = 0L;
  private long last = 0L;
  private int calls = 0;

  public CallCounter() {
    description = "calls";
  }

  public CallCounter(String description) {
    this.description = description;
  }

  public void call() {
    now = System.currentTimeMillis();
    calls++;
    long diff = now - last;
    if (diff >= 1000) {
      System.out.println(description + ": " + calls);
      calls = 0;
      last = now;
    }
  }
}
