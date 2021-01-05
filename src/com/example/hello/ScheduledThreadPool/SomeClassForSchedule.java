package com.example.hello.ScheduledThreadPool;

import java.util.concurrent.CountDownLatch;

public class SomeClassForSchedule {
  private CountDownLatch countDownLatch;
  static String msg = "QQQ";

  SomeClassForSchedule (CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }
  String getMessage() {
    try {
      countDownLatch.countDown();
      return SomeClassForSchedule.msg;
    } finally {
      System.out.printf("Msg from thread: %s%n", SomeClassForSchedule.msg);
    }
  }
}
