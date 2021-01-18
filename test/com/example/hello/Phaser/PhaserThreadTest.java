package com.example.hello.Phaser;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class PhaserThreadTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(PhaserThread.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  enum phases {
    BEGINNING(0),
    FIRST(1),
    SECOND(2);

    private int phaseNumber;
    phases(int phaseNumber) {
      this.phaseNumber = phaseNumber;
    }

    public int getPhaseNum() {
      return this.phaseNumber;
    }
  }

  @Test
  public void CheckWhetherThreadRegisterInPhaseOrNot() {
    final int THREADS_COUNT_FOR_FIRST_PHASE = 5;
    final int THREADS_COUNT_FOR_SECOND_PHASE = 3;
    ExecutorService executorService = Executors.newCachedThreadPool();
    Phaser phaser = new Phaser(1);

    assertEquals(phases.BEGINNING.getPhaseNum(), phaser.getPhase());

    IntStream.range(0, THREADS_COUNT_FOR_FIRST_PHASE).forEach(index -> {
      executorService.execute(new PhaserThread("thread-" + THREADS_COUNT_FOR_FIRST_PHASE + index, phaser));
    });

    phaser.arriveAndAwaitAdvance();
    assertEquals(phases.FIRST.getPhaseNum(), phaser.getPhase());

    IntStream.range(0, THREADS_COUNT_FOR_SECOND_PHASE).forEach(index -> {
      executorService.execute(new PhaserThread("thread-" + THREADS_COUNT_FOR_SECOND_PHASE + index, phaser));
    });

    phaser.arriveAndAwaitAdvance();
    assertEquals(phases.SECOND.getPhaseNum(), phaser.getPhase());

    phaser.arriveAndDeregister();
    executorService.shutdown();
  }
}
