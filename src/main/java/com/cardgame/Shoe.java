package com.cardgame;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Shoe {
  private List<Card> allDecksCards = new ArrayList();
  private AtomicInteger dealingCursor = new AtomicInteger();

  public void addDeck(Deck deck) {
    allDecksCards.addAll(deck.getCards());
  }

  public List<Card> nextCards(int amount) {
    List<Card> dealtCards = new ArrayList<Card>();
    int dealtCardsAmount = 0;
    while (dealtCardsAmount < amount && dealingCursor.get() < allDecksCards.size()) {
      dealtCards.add(allDecksCards.get(dealingCursor.get()));
      dealtCardsAmount++;
      dealingCursor.getAndIncrement();
    }
    return dealtCards;
  }

  public List<SuitCount> getUndealtCardBySuit() {
    List<SuitCount> suitCountList = new ArrayList<SuitCount>(4);
    for (Card.Suit suit : Card.Suit.values()) {
      SuitCount suitCount = new SuitCount(suit, 0);
      suitCountList.add(suitCount);
    }
    for (int i = dealingCursor.get(); i < allDecksCards.size(); i++) {
      Card card = allDecksCards.get(i);
      SuitCount suitCount = suitCountList.get(card.getSuit().ordinal());
      suitCount.incrementCount();
    }
    return suitCountList;
  }

  public List<Card> getUndealtCard() {
    List<Card> cardList = new ArrayList<Card>();
    for (int i = dealingCursor.get(); i < allDecksCards.size(); i++) {
      Card card = new Card(allDecksCards.get(i));
      cardList.add(card);
    }
    Collections.sort(
        cardList,
        (c1, c2) ->
            13 * (c2.getSuit().ordinal() - c1.getSuit().ordinal()) + c2.getValue() - c1.getValue());

    return cardList;
  }

  public void shuffle() {

    this.allDecksCards = CardsUtil.shuffle(allDecksCards, (int)dealingCursor.get(),allDecksCards.size() );
  }
}
