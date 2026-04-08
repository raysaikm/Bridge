package org.srm.test.bridge.bid;

import java.util.function.Predicate;

public interface Situation extends Predicate<Auction> {

    static Situation isOpeningBid() {
        return bids -> bids.size() == 0 || bids.allMatch(b -> b == Bid.PASS);
    }

    static Situation isThirdChair() {
        return bids -> bids.size() == 2 && bids.allMatch(b -> b == Bid.PASS);
    }

    static Situation isBalancingSeat() {
        return bids -> bids.size() == 3 && bids.allMatch(b -> b == Bid.PASS);
    }

    static Situation isResponseToOpening() {
        return bids -> bids.size() == 2 && bids.last(0) != Bid.PASS;
    }

    static Situation isResponseToOpening(Bid bid) {
        return bids -> bids.size() > 2 && bids.last(0) == bid;
    }

    static Situation isResponseToUncontestedOpening(Bid bid) {
        return bids -> bids.size() > 2 && bids.last(0) == bid && bids.last(1) == Bid.PASS;
    }

    static Situation isOverCall(Bid bid) {
        return bids -> bids.size() > 1 && bids.last(0) == bid;
    }
}