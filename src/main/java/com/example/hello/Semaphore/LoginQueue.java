package com.example.hello.Semaphore;

import java.util.concurrent.Semaphore;

public class LoginQueue {
  private Semaphore semaphore;

  public LoginQueue(int slots) {
    semaphore = new Semaphore(slots);
  }

  public boolean tryLogin() {
    return semaphore.tryAcquire();
  }

  public void logout() {
    semaphore.release();
  }

  public int emptySlots() {
    return semaphore.availablePermits();
  }
}
