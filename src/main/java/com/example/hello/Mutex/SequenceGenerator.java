package com.example.hello.Mutex;

public class SequenceGenerator implements SeqGen {
  private int sequence = 0;

  public int generate() {
    sequence = sequence + 1;
    return sequence;
  }
}
