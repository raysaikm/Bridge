package org.srm.test.bridge.bid;

import org.srm.test.bridge.card.Suit;

import static org.srm.test.bridge.card.Suit.*;
public enum Bid {
    PASS, DOUBLE, REDOUBLE
    ,_1C(1,CLUB) ,_1D(1,DIAMOND) ,_1H(1,HEART) ,_1S(1,SPADE) ,_1N(1,NOTRUMP)
    ,_2C(2,CLUB) ,_2D(2,DIAMOND) ,_2H(2,HEART) ,_2S(2,SPADE) ,_2N(2,NOTRUMP)
    ,_3C(3,CLUB) ,_3D(3,DIAMOND) ,_3H(3,HEART) ,_3S(3,SPADE) ,_3N(3,NOTRUMP)
    ,_4C(4,CLUB) ,_4D(4,DIAMOND) ,_4H(4,HEART) ,_4S(4,SPADE) ,_4N(4,NOTRUMP)
    ,_5C(5,CLUB) ,_5D(5,DIAMOND) ,_5H(5,HEART) ,_5S(5,SPADE) ,_5N(5,NOTRUMP)
    ,_6C(6,CLUB) ,_6D(6,DIAMOND) ,_6H(6,HEART) ,_6S(6,SPADE) ,_6N(6,NOTRUMP)
    ,_7C(7,CLUB) ,_7D(7,DIAMOND) ,_7H(7,HEART) ,_7S(7,SPADE) ,_7N(7,NOTRUMP) ;


    private final int level;
    private final Suit suit;

    Bid(){
        this(0, null);
    }

    Bid(int level, Suit suit) {
        this.level = level;
        this.suit = suit;
    }
}