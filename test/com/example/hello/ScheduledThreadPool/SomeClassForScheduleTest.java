package com.example.hello.ScheduledThreadPool;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.*;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SomeClassForScheduleTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(SomeClassForSchedule.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Test
  public void getMessage() throws InterruptedException, ExecutionException {
    final int THREAD_COUNT = 3;
    final int LOCK_NUM = 5;
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREAD_COUNT);
    CountDownLatch countDownLatch = new CountDownLatch(LOCK_NUM);
    SomeClassForSchedule someClassForSchedule = new SomeClassForSchedule(countDownLatch);

    ScheduledFuture<?> scheduledFutures =
      executorService.schedule(someClassForSchedule::getMessage, 3000, TimeUnit.MILLISECONDS);
    countDownLatch.await(1000, TimeUnit.MILLISECONDS);

    assertEquals(SomeClassForSchedule.msg, scheduledFutures.get());

    scheduledFutures.cancel(true);
    executorService.shutdown();
  }
}
