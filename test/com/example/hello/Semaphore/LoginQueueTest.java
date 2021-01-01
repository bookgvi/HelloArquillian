package com.example.hello.Semaphore;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class LoginQueueTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(LoginQueue.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Test
  public void trySemaphore() {
    final int SLOTS = 10;
    ExecutorService executorService = Executors.newFixedThreadPool(SLOTS);
    LoginQueue loginQueue = new LoginQueue(SLOTS);
    IntStream.range(0, SLOTS + 1).forEach(user -> executorService.execute(loginQueue::tryLogin)
    );
    executorService.shutdown();

    assertEquals(0, loginQueue.emptySlots());
    assertFalse(loginQueue.tryLogin());

    loginQueue.logout();

    assertTrue(loginQueue.emptySlots() > 0);
    assertTrue(loginQueue.tryLogin());
  }

}
