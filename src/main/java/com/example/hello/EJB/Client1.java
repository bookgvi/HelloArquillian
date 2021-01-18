package com.example.hello.EJB;

import com.example.hello.EJB.StatelessEJB;

import javax.ejb.EJB;

public class Client1 {
  public String clientName = "Client 1";
  public void showName() {
    System.out.print(clientName);
  }
  @EJB
  StatelessEJB statelessEJB;
}
