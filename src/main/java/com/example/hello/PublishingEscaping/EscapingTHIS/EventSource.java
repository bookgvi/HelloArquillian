package com.example.hello.PublishingEscaping.EscapingTHIS;

import java.util.EventListener;

class EventSource {
  Object eventListener;
  void registerSource(Object source) {
    eventListener = source;
  }
}
