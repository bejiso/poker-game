package com.cardgame;

public class SuitCount {
  private final Card.Suit suit;
  private int count;

  public SuitCount(Card.Suit suit, int count) {
    this.suit = suit;
    this.count = count;
  }

  public Card.Suit getSuit() {
    return suit;
  }

  public int getCount() {
    return count;
  }

  public void incrementCount() {
    this.count++;
  }
}
