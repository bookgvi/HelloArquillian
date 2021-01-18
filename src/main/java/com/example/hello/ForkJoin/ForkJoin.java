package com.example.hello.ForkJoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ForkJoin {
  public static int THRESHOLD = 4;
  public static void main(String[] args) {
    final String msg = "This is the test message!!!";
    final int MAX_NUM = 1_000_000;
    final int[] arrayOfInt = IntStream.rangeClosed(1, MAX_NUM).toArray();

    CustomVoidRecursiveTask customVoidRecursiveTask = new CustomVoidRecursiveTask(msg);
    CustomStringRecursiveTask customStringRecursiveTask = new CustomStringRecursiveTask(msg);
    Summarization summarization = new Summarization(arrayOfInt);
//    ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    ForkJoinPool forkJoinPool = new ForkJoinPool(6);

    forkJoinPool.invoke(customVoidRecursiveTask);
//    String result = forkJoinPool.invoke(customStringRecursiveTask);
    forkJoinPool.submit(customStringRecursiveTask);
    String result = customStringRecursiveTask.join();
    System.out.printf("%s -> %s%n", msg, result);

    forkJoinPool.submit(summarization);
    Integer sum = summarization.join();
    System.out.printf("Sum of %s integers = %s%n", MAX_NUM, sum);

    forkJoinPool.shutdown();
  }
}
