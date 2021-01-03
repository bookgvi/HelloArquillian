package com.example.hello.WeakReference;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class WeakReferenceForTestTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(WeakReferenceForTest.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Test
  public void ChekReferenceQueue_ForWeakRefernce() {
    Reference ref;
    StringBuilder sb = new StringBuilder();
    ReferenceQueue<StringBuilder> queue = new ReferenceQueue<>();
    WeakReference<StringBuilder> weakSb = new WeakReference<>(sb, queue);
    sb = null;
    System.gc();
    while ((ref = queue.poll()) == null) {
      try {
        sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    assertNotNull(ref);
  }
}
