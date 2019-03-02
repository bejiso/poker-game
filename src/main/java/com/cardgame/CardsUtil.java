package com.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CardsUtil {

  static final int MIN_SHUFFLE_ITER = 10;
  static final int MAX_SHUFFLE_ITER = 50;


  public static List<Card> deckCards() {
    List<Card> deckCards = new ArrayList<Card>();
    for (Card.Suit suit : Card.Suit.values()) {
      for (Card.FaceValue faceValue : Card.FaceValue.values()) {
        deckCards.add(new Card(suit, faceValue));
      }
    }
    return deckCards;
  }

  public static List<Card> shuffle(List<Card> cards, int startingPosition, int nbCards) {

    Random rand = new Random();
    int nbIterations = rand.nextInt((MAX_SHUFFLE_ITER - MIN_SHUFFLE_ITER) + 1) + MIN_SHUFFLE_ITER;
    rand = new Random();

    for (int i = 0; i < nbIterations; i++) {
      int firstCard = rand.nextInt((nbCards - startingPosition) ) + startingPosition;
      int secondCard = rand.nextInt((nbCards - startingPosition) ) + startingPosition;
      Collections.swap(cards, firstCard, secondCard);
    }
    return cards;
  }

  public static int totalScore(List<Card> cards) {
    int totalScore = 0;
    for (int i = 0; i < cards.size(); i++) {
      totalScore += cards.get(i).getValue();
    }
    return totalScore;
  }
}
