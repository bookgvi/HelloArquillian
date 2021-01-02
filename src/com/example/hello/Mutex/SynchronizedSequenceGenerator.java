package com.example.hello.Mutex;

public class SynchronizedSequenceGenerator extends SequenceGenerator {
  @Override
  public synchronized int generate() {
    return super.generate();
  }
}
