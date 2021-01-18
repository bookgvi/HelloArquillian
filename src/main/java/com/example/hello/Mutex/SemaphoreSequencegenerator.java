package com.example.hello.Mutex;

import java.util.concurrent.Semaphore;

public class SemaphoreSequencegenerator extends SequenceGenerator {
  private Semaphore mutex = new Semaphore(1);

  @Override
  public int generate() {
    try {
      mutex.acquire();
      return super.generate();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      mutex.release();
    }
    return 0;
  }
}
