package com.cardgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PokerApp {

  public static void main(String[] args) {
    SpringApplication.run(PokerService.class, args);
  }
}
