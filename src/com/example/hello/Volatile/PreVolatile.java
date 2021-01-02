package com.example.hello.Volatile;

import java.util.concurrent.Callable;

public class PreVolatile implements Callable<Long> {
  public boolean flag = false;
  public long number = 0;

  public Long call() {
    while (!flag) {
      Thread.yield();
    }
    return number;
  }
}
