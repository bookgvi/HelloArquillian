package com.example.hello.PublishingEscaping;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class PublishingEscapingTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(PublishingEscaping.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Inject
  PublishingEscaping publishingEscaping;

  @Test
  public void getStrArray() {
    assertEquals("Q", publishingEscaping.getStrArray()[0]);
    String[] newStrArray = publishingEscaping.getStrArray();
    newStrArray[0] = "QQQ";
    assertNotEquals("Q", publishingEscaping.getStrArray()[0]);
    assertEquals("QQQ", publishingEscaping.getStrArray()[0]);
  }
}
