package com.cardgame;

import java.util.List;

public class Deck {

  private final int id;
  private List<Card> deckCards;

  public Deck(int id) {
    this.id = id;
    this.deckCards = CardsUtil.deckCards();
  }

  public int getId() {
    return this.id;
  }

  public List<Card> getCards() {
    return deckCards;
  }
}
