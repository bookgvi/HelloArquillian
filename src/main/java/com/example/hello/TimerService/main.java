package com.example.hello.TimerService;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class main {
  public static void main(String[] args) throws Exception {
    try (SeContainer seContainer = SeContainerInitializer.newInstance().initialize()) {
      ProgramTimerService programTimerService = seContainer.select(ProgramTimerService.class).get();
      programTimerService.createTimer();
    }
  }
}
