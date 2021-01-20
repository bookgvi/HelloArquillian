package com.example.hello.PublishingEscaping.EscapingTHIS;

import java.util.EventListener;

public class PublishingThis {
  private int SECRET_INTEGER = 666;

  PublishingThis(EventSource source)  {
    source.registerSource(new NestedClass());
  }

  class NestedClass {
//    int QQQ = 123;
    Object getThis() {
      return null;
    }
  }
}

