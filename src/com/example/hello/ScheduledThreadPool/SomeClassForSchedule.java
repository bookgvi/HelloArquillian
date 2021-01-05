package com.example.hello.ScheduledThreadPool;

import java.util.concurrent.CountDownLatch;

public class SomeClassForSchedule {
  private CountDownLatch countDownLatch;
  static String msg = "QQQ";

  SomeClassForSchedule (CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }
  String getMessage() {
    System.out.printf("Msg: %s%n", SomeClassForSchedule.msg);
    try {
      return SomeClassForSchedule.msg;
    } finally {
      countDownLatch.countDown();
    }
  }
}
