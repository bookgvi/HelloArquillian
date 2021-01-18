package com.example.hello.ForkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class CustomStringRecursiveTask extends RecursiveTask<String> {
  private String workload;

  public CustomStringRecursiveTask(String workload) {
    this.workload = workload;
  }

  @Override
  protected String compute() {
    if (workload.length() > ForkJoin.THRESHOLD) {
      return ForkJoinTask.invokeAll(createSubTasks())
        .stream()
        .map(ForkJoinTask::join)
        .collect(Collectors.joining());
    }
    return proceed();
  }

  private String proceed() {
     return workload.toUpperCase();
  }

  private List<CustomStringRecursiveTask> createSubTasks() {
    List<CustomStringRecursiveTask> resultArr = new ArrayList<>();
    String partOne = workload.substring(0, workload.length() / 2);
    String partTwo = workload.substring(workload.length() / 2);
    resultArr.add(new CustomStringRecursiveTask(partOne));
    resultArr.add(new CustomStringRecursiveTask(partTwo));
    return resultArr;
  }
}
