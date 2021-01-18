package com.example.hello.Phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class MainThread {
  public static void main(String[] args) {
    final int FIRST_PHASE_THREAD_COUNT = 5;
    final int SECOND_PHASE_THREAD_COUNT = 5;
    Phaser phaser = new Phaser(1);
    ExecutorService executorService = Executors.newCachedThreadPool();

    phaser.arriveAndAwaitAdvance(); // Skip phase#0

    IntStream.range(0, FIRST_PHASE_THREAD_COUNT).forEach(threadNum -> {
      executorService.submit(new PhaserThread("thread-" + (FIRST_PHASE_THREAD_COUNT - threadNum), phaser));
    });
    int phase = phaser.getPhase();
    phaser.arriveAndAwaitAdvance();
    System.out.printf("----------%nPhase %s complete %n%n", phase);

    IntStream.range(0, SECOND_PHASE_THREAD_COUNT).forEach(threadNum -> {
      executorService.submit(new PhaserThread("thread-" + (SECOND_PHASE_THREAD_COUNT + 1 + threadNum), phaser));
    });
    phase = phaser.getPhase();
    phaser.arriveAndAwaitAdvance();
    System.out.printf("----------%nPhase %s complete %n%n", phase);

    phaser.arriveAndDeregister();
    System.out.printf("----------%nApplication finished after phase %s %n%n", phaser.getPhase());
    executorService.shutdown();
  }
}
