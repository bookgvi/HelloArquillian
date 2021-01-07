package com.example.hello.String;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SimpleStringClassTest {
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
      .addClass(SimpleStringClass.class)
      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @Test
  public void CompareStrings() {
    String str1 = "QQQ";
    String str2 = "QQQ";
    String str3 = new String("QQQ");
    String str4 = str3.intern();

    assertSame(str2, str1);
    assertSame(str4, str1);
    assertNotSame(str2, str3);
  }
}
