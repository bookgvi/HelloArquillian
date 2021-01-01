package com.example.hello.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class StatelessEJBTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(StatelessEJB.class)
      .addClass(Client1.class)
      .addClass(Client2.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Inject
  Client1 ejbClient1;

  @Inject
  Client2 ejbClient2;

  @Test
  public void givenOneStatelessBean_whenStateIsSetInOneBean_secondBeanShouldHaveSameState() {

    ejbClient1.statelessEJB.name = "Client 1";
    Assert.assertEquals("Client 1", ejbClient1.statelessEJB.name);
    Assert.assertEquals("Client 1", ejbClient2.statelessEJB.name);
  }

  @Test
  public void givenOneStatelessBean_whenStateIsSetInBothBeans_secondBeanShouldHaveSecondBeanState() {

    ejbClient1.statelessEJB.name = "Client 1";
    ejbClient2.statelessEJB.name = "Client 2";
    Assert.assertEquals("Client 2", ejbClient2.statelessEJB.name);
  }


}
