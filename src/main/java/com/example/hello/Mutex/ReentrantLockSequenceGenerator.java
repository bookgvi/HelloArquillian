package com.example.hello.Mutex;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSequenceGenerator extends SequenceGenerator {
  private ReentrantLock mutex = new ReentrantLock();
  @Override
  public int generate() {
    try {
      mutex.lock();
      return super.generate();
    } finally {
      mutex.unlock();
    }
  }
}
