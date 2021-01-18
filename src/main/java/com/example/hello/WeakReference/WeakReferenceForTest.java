package com.example.hello.WeakReference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import static java.lang.Thread.sleep;

public class WeakReferenceForTest {

  public static void main(String[] args) throws InterruptedException {
    Reference ref;
    StringBuilder sb = new StringBuilder();
    ReferenceQueue<StringBuilder> queue = new ReferenceQueue<>();
    WeakReference<StringBuilder> weakSb = new WeakReference<>(sb, queue);

    sb = null;
    System.gc();
    while ((ref = queue.poll()) == null) {
      sleep(500);
    }
    System.out.printf("Ref: %s%n", ref);
  }
}
