package com.example.hello.ThreadPool;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SomeCallableClassTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(SomeCallableClass.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Inject
  SomeCallableClass sc;

  @Test
  public void getSomeString() {
    final int THREADS_COUNT = 17;
    final int CORE_POOL_SIZE = 3;
    final int MAXIMUM_POOL_SIZE = 10;
    BlockingDeque<Runnable> workingQueue = new LinkedBlockingDeque<>();
    List<Future<String>> futureList = new ArrayList<>();

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
      CORE_POOL_SIZE,
      MAXIMUM_POOL_SIZE,
      0L, TimeUnit.MILLISECONDS,
      workingQueue
    );
    IntStream.range(0, THREADS_COUNT).forEach(index -> futureList.add(((ExecutorService) threadPoolExecutor).submit(sc::getSomeString)));

    assertEquals(THREADS_COUNT, futureList.size());
    assertEquals(CORE_POOL_SIZE, threadPoolExecutor.getCorePoolSize());
    assertEquals(0, threadPoolExecutor.getCompletedTaskCount()); // Because of sleep in ThreadClass
    assertEquals(THREADS_COUNT - CORE_POOL_SIZE, threadPoolExecutor.getQueue().size());
    assertEquals(CORE_POOL_SIZE, threadPoolExecutor.getPoolSize());

    ((ExecutorService) threadPoolExecutor).shutdown();
  }
}
