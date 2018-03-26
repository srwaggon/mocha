package mocha.shared.task.event;

import lombok.Data;

@Data
public class TaskEvent {
  private final Runnable runnable;
}
