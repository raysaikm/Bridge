package org.srm.test.bridge.bid.system;

import org.srm.test.bridge.Hand;
import org.srm.test.bridge.bid.Auction;
import org.srm.test.bridge.bid.Bid;
import org.srm.test.bridge.bid.Evaluation;
import org.srm.test.bridge.bid.Situation;

import java.util.Arrays;
import java.util.List;

public class Rule {

  record BiddingResult(Bid bid, Rule triggeredRule) {

  }

  private int priority;
  private final Evaluation evaluation;
  private final List<Situation> situation;
  private final BiddingResult suggestedBid;
  private final String description;

  public Rule(int priority, String description, Evaluation evaluation, Bid bid, Situation... situation) {
    this.priority = priority;
    this.description = description;
    this.evaluation = evaluation;
    this.situation = Arrays.asList(situation);
    this.suggestedBid = new BiddingResult(bid, this);
  }

  public boolean match(final Auction auction) {
    return situation.stream().allMatch(s -> s.test(auction));
  }

  public boolean match(final Hand hand, final Auction auction) {
    return evaluation.test(hand) && match(auction);
  }

  public int getPriority() {
    return priority;
  }

  public BiddingResult getSuggestedBid() {
    return suggestedBid;
  }
}
