package org.srm.test.bridge.bid;

import org.srm.test.bridge.Player;

import java.util.Stack;
import java.util.function.Predicate;

public class Auction {
    public Stack<Bid> bids = new Stack<>();

    public Player dealer = null;

    public int size() { return bids.size(); }

    public Bid last(int n) {
        return n > bids.size()? null :bids.elementAt(bids.size() - n - 1);
    }

    public boolean allMatch(Predicate<Bid> condition) {
        return bids.stream().allMatch(condition);
    }

    public void renew() {
        bids = new Stack<>();
        if (dealer != null) dealer = dealer.lho();
    }

    public void nextBid(Bid bid) {
        bids.push(bid);
    }

}