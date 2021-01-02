package com.example.hello.Phaser;

import java.util.concurrent.Phaser;

public class PhaserThread implements Runnable {
  private final Phaser phaser;
  private String threadName;

  PhaserThread(Phaser phaser, String threadName) {
    this.phaser = phaser;
    this.threadName = threadName;
    phaser.register();
  }

  @Override
  public void run() {
    phaser.arriveAndAwaitAdvance();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      phaser.arriveAndDeregister();
    }
  }
}
