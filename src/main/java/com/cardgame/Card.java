package com.cardgame;

public class Card {

    private final Suit suit;
    private final FaceValue faceValue;

    public Card(Suit suit, FaceValue faceValue) {
        this.suit = suit;
        this.faceValue = faceValue;
    }

    public int getValue() {
        return faceValue.value;
    }

    public FaceValue getFaceValue() {
        return faceValue;
    }

    public Suit getSuit() {
        return suit;
    }


    enum Suit{
        HEARTS,
        SPADES,
        CLUBS,
        DIAMONDS
    }

    enum FaceValue {
        ACE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13);

        private final int value;

        FaceValue(int value){
            this.value=value;
        }


    }


}


