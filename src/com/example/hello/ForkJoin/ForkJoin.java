package com.example.hello.ForkJoin;

import java.util.concurrent.ForkJoinPool;

public class ForkJoin {
  public static int THRESHOLD = 4;
  public static void main(String[] args) {
    final String msg = "This is the test message!!!";
    CustomVoidRecursiveTask customVoidRecursiveTask = new CustomVoidRecursiveTask(msg);
    CustomStringRecursiveTask customStringRecursiveTask = new CustomStringRecursiveTask(msg);
//    ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    ForkJoinPool forkJoinPool = new ForkJoinPool(6);

    forkJoinPool.invoke(customVoidRecursiveTask);
    String result = forkJoinPool.invoke(customStringRecursiveTask);
    System.out.printf("%s -> %s%n", msg, result);

    forkJoinPool.shutdown();
  }
}
