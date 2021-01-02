package com.example.hello.Volatile;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.concurrent.*;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class PreVolatileTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(PreVolatile.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Inject
  PreVolatile preVolatile;

  @Test
  public void callZZZ() throws ExecutionException, InterruptedException {
    FutureTask<Long> future = new FutureTask<>(preVolatile);
    new Thread(future).start();
    preVolatile.number = 666;
    preVolatile.flag = true;
    assertEquals(Long.valueOf(666), future.get());
  }
}
