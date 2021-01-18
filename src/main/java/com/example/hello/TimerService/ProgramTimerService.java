package com.example.hello.TimerService;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.logging.Logger;

@Stateless
public class ProgramTimerService {
  @Resource
  TimerService timerService;

  void createTimer() throws Exception {
    timerService.createTimer(10000, "CreatedTimer");
    ScheduleExpression expression = new ScheduleExpression();
    expression.second("*/1").minute("*").hour("*");
    TimerConfig timerConfig = new TimerConfig();
    timerConfig.setInfo("Periodical timer event - every 1 sec");
    timerService.createCalendarTimer(expression, timerConfig);
  }

  @Timeout
  void timerAction(Timer timer) {
    Logger.getLogger("TimerService").info(timer.getInfo().toString());
  }
}
