package mocha.shared.task;

import com.google.common.eventbus.Subscribe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mocha.shared.task.event.TaskEvent;

public class TaskService {

  private ExecutorService taskService = Executors.newCachedThreadPool();

  @Subscribe
  public void handle(TaskEvent taskEvent) {
    taskService.submit(taskEvent.getRunnable());
  }

  public void submit(Runnable runnable) {
    taskService.submit(runnable);
  }
}
