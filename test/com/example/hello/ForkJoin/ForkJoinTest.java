package com.example.hello.ForkJoin;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ForkJoinTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(ForkJoin.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }
  @Test
  public void Sum_of_natural_quantities() throws ExecutionException, InterruptedException {
    final long LOW_BOUND = 1L;
    final long HIGH_BOUND = 1_000_000L;

    ForkJoinPool forkJoinPool = new ForkJoinPool(6);

    List<Long> arrList = LongStream.rangeClosed(LOW_BOUND, HIGH_BOUND).boxed().collect(Collectors.toList());
    Long sum = forkJoinPool.submit(() -> arrList.parallelStream().reduce(0L, Long::sum)).get();

    int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10};
    int result = Arrays.stream(arr).sum();
    int result1 = IntStream.range(1, 11).sum();
    int result2 = IntStream.rangeClosed(1, 10).sum();

    assertEquals(55, result);
    assertEquals(55, result1);
    assertEquals(55, result2);
    assertEquals((LOW_BOUND + HIGH_BOUND) * HIGH_BOUND / 2, sum.longValue());
  }

}
