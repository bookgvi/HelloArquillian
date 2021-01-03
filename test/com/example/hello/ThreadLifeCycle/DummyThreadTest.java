package com.example.hello.ThreadLifeCycle;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class DummyThreadTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(DummyThread.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Test
  public void getStatus() throws InterruptedException {
    final int THREADS_COUNT = 2;
    DummyThread dummyThread = new DummyThread();
    Thread t1 = new Thread(dummyThread);
    Thread t2 = new Thread(dummyThread);
    t1.start();
    t2.start();
    Thread.sleep(1000);
    assertEquals(Thread.State.RUNNABLE, t1.getState());
    assertEquals(Thread.State.BLOCKED, t2.getState());
  }
}
