package com.example.hello.ForkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

public class CustomVoidRecursiveTask extends RecursiveAction {
  private String workload;
  private Logger logger = Logger.getAnonymousLogger();

  public CustomVoidRecursiveTask(String workload) {
    this.workload = workload;
  }

  @Override
  protected void compute() {
    if (workload.length() > ForkJoin.THRESHOLD) {
      ForkJoinTask.invokeAll(splitToSubTasks());
    } else {
      proceed();
    }
  }

  private void proceed() {
    String upperCaseString = workload.toUpperCase();
    logger.info(workload + " -> " + upperCaseString);
  }

  private List<CustomVoidRecursiveTask> splitToSubTasks() {
    List<CustomVoidRecursiveTask> subTasks = new ArrayList<>();
    String newWorkloadPartOne = workload.substring(0, workload.length() / 2);
    String newWorkloadPartTwo = workload.substring(workload.length() / 2);
    subTasks.add(new CustomVoidRecursiveTask(newWorkloadPartOne));
    subTasks.add(new CustomVoidRecursiveTask(newWorkloadPartTwo));
    return subTasks;
  }
}
