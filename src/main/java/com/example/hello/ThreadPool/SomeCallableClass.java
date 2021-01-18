package com.example.hello.ThreadPool;

public class SomeCallableClass {
  private String resultString = "QQQ";

  public String getSomeString() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    return resultString;
  }
}
