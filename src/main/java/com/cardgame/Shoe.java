package com.cardgame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Shoe {
    private List<Card> allDecksCards = new ArrayList();
    private AtomicInteger dealingCursor=new AtomicInteger() ;

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

    public void shuffle() {

        this.allDecksCards = CardsUtil.shuffle(allDecksCards,dealingCursor.get());
    }
}