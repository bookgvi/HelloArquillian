package com.example.hello.Mutex;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SequenceGeneratorTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(SequenceGenerator.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  private final int NUM = 1000;
  private final int THREADS_COUNT = 5;

  @Test
  public void testMultiThreadSequenceFill() throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(THREADS_COUNT);
    Set<Integer> sequence = getSequence(new SequenceGenerator(), executorService);
    Assert.assertEquals(NUM, sequence.size());
  }

  private Set<Integer> getSequence(SequenceGenerator sequenceGenerator, ExecutorService executorService) throws ExecutionException, InterruptedException {
    List<Future<Integer>> futures = new ArrayList<>();
    Set<Integer> sequence = new LinkedHashSet<>();
    for (int i = 0; i < NUM; i++) {
      futures.add(executorService.submit(sequenceGenerator::generate));
    }
    for (Future<Integer> future: futures) {
      sequence.add(future.get());
    }

    executorService.awaitTermination(1, TimeUnit.SECONDS);
    executorService.shutdown();

    return sequence;
  }
}
