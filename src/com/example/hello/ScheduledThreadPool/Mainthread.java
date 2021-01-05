package com.example.hello.ScheduledThreadPool;

import java.util.concurrent.*;

public class Mainthread {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    final int THREAD_COUNT = 3;
    final int LOCK_NUM = 5;
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREAD_COUNT);
    CountDownLatch countDownLatch = new CountDownLatch(LOCK_NUM);
    SomeClassForSchedule someClassForSchedule = new SomeClassForSchedule(countDownLatch);

    ScheduledFuture<?> scheduledFutures =
      executorService.scheduleAtFixedRate(someClassForSchedule::getMessage, 300, 100, TimeUnit.MILLISECONDS);
    countDownLatch.await(1000, TimeUnit.MILLISECONDS);
    scheduledFutures.cancel(true);

//    System.out.printf("Msg: %s%n", scheduledFutures.get());

    executorService.shutdown();

  }
}
