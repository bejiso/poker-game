package com.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardsUtil {

    final static int MIN_SHUFFLE_ITER=10;
    final static int MAX_SHUFFLE_ITER=50;
    final static int NB_PACK_CARDS=52;

    public static List<Card> deckCards() {
        List<Card> deckCards = new ArrayList<Card>();
        for (Card.Suit suit: Card.Suit.values()) {
            for (Card.FaceValue faceValue: Card.FaceValue.values()) {
                deckCards.add(new Card(suit, faceValue));
            }
        }
        return deckCards;
    }


    public static List<Card> shuffle(List<Card> cards, int startingPosition) {

        Random rand = new Random();
        int nbIterations= rand.nextInt((MAX_SHUFFLE_ITER - MIN_SHUFFLE_ITER) + 1)
                + MIN_SHUFFLE_ITER;
        rand=new Random();

        for(int i = 0 ; i < nbIterations ; i ++){
            int firstCard = rand.nextInt((NB_PACK_CARDS-startingPosition)+1)+startingPosition;
            int secondCard = rand.nextInt((NB_PACK_CARDS-startingPosition)+1)+startingPosition;
            Collections.swap(cards,firstCard,secondCard);
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
