package com.example.hello.PublishingEscaping.EscapingTHIS;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MainApp {
  public static void main(String[] args) throws IllegalAccessException {
    EventSource eventSource;

    PublishingThis publishingThis = new PublishingThis(eventSource = new EventSource());
    System.out.println(eventSource.eventListener.getClass().getDeclaredFields().length);
    for (Field field : eventSource.eventListener.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      if (field.get(eventSource.eventListener).getClass().getName().contains("PublishingThis")) {
        for (Field _field : field.get(eventSource.eventListener).getClass().getDeclaredFields()) {
          _field.setAccessible(true);
          System.out.printf("%s %s %s: %s%n",
            Modifier.isPrivate(_field.getModifiers()) ? "private" : "non-private",
            _field.getType().getName(),
            _field.getName(),
            _field.get(field.get(eventSource.eventListener))
          );
        }
      }
    }
  }
}
