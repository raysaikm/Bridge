package org.srm.test.bridge.bid;

import org.srm.test.bridge.Hand;
import org.srm.test.bridge.card.Suit;

import java.util.function.Predicate;

public interface Evaluation extends Predicate<Hand> {

    static Evaluation any() { return hand -> true; }

    static Evaluation hcp(int min) {
        return hand -> hand.hcp() >= min;
    }

    static Evaluation hcp(int min, int max) {
        return hand -> hand.hcp() >= min && hand.hcp()<=max;
    }

    static Evaluation total(int min) {
        return hand -> hand.hcp() + hand.lp() >= min;
    }

    static Evaluation total(int min, int max) {
        return hand -> hand.hcp() + hand.lp() >= min && hand.hcp() + hand.shape().lp() <=max;
    }

    static Evaluation total(Suit trump, int min) {
        return hand -> hand.hcp() + hand.sp(trump) >= min;
    }

    static Evaluation total(Suit trump, int min, int max) {
        return hand -> hand.hcp() + hand.sp(trump) >= min && hand.hcp() + hand.sp(trump) <=max;
    }
}