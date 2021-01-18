package com.example.hello.ForkJoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Summarization extends RecursiveTask<Integer> {
  int[] array;
  private final int TRESHOLD = 2;

  public Summarization(int[] array) {
    this.array = array;
  }

  @Override
  protected Integer compute() {
    if (array.length > TRESHOLD) {
      return ForkJoinTask.invokeAll(createSubTasks())
        .stream()
        .mapToInt(ForkJoinTask::join)
        .sum();
    }
    return proceedSum();
  }

  private List<Summarization> createSubTasks() {
    List<Summarization> result = new ArrayList<>();
    int[] firstPart = Arrays.copyOfRange(array, 0, array.length / 2);
    int[] secondPart = Arrays.copyOfRange(array, array.length / 2, array.length);
    result.add(new Summarization(firstPart));
    result.add(new Summarization(secondPart));
    return result;
  }

  private Integer proceedSum() {
    return Arrays.stream(array).sum();
  }
}
