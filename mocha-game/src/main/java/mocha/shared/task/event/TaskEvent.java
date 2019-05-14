package mocha.shared.task.event;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class TaskEvent {

  private Runnable runnable;

}
