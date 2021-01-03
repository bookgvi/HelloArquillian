package com.example.hello.ThreadLifeCycle;

import oracle.jvm.hotspot.jfr.ThreadStates;

import java.util.concurrent.Callable;

public class DummyThread implements Runnable {

  public void run() {
    infinityLoop();
  }

  private synchronized void infinityLoop() {
    while(true) {
    }
  }
}
