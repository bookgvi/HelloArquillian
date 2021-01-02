package com.example.hello.Mutex;

public class SequenceGenerator {
  private int sequence = 0;

  public int generate() {
    sequence = sequence + 1;
    return sequence;
  }
}
