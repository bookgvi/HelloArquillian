package com.example.hello.ScheduledThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Mainthread {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    final int THREAD_COUNT = 3;
    final int LOCK_NUM = 3;
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREAD_COUNT);
    CountDownLatch countDownLatch = new CountDownLatch(LOCK_NUM);
    SomeClassForSchedule someClassForSchedule = new SomeClassForSchedule(countDownLatch);
    List<String> futureList = new ArrayList<>();

    ScheduledFuture<?> scheduledFutures =
      executorService.schedule(someClassForSchedule::getMessage, 300, TimeUnit.MILLISECONDS);

    countDownLatch.await(1000, TimeUnit.MILLISECONDS);
    System.out.printf("Msg from main thread: %s%n", scheduledFutures.get());
    scheduledFutures.cancel(true);

    executorService.shutdown();

  }
}
