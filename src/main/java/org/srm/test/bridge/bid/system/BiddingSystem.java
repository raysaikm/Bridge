package org.srm.test.bridge.bid.system;

import java.util.List;
import org.srm.test.bridge.Hand;
import org.srm.test.bridge.bid.Auction;
import org.srm.test.bridge.bid.Bid;

public interface BiddingSystem {

  List<Rule> getRules();

  void addRule(Rule rule);

  void addConvention(Convention convention);

  Bid getNextBid(Hand hand, Auction auction);
}
