package com.cardgame;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int id;
    private final List<Card> cards = new ArrayList<Card>();

    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void dealCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

}
