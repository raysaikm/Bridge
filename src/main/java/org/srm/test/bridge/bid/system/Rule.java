package org.srm.test.bridge.bid.system;

import org.srm.test.bridge.Hand;
import org.srm.test.bridge.bid.Auction;
import org.srm.test.bridge.bid.Bid;
import org.srm.test.bridge.bid.Evaluation;
import org.srm.test.bridge.bid.Situation;

import java.util.Arrays;
import java.util.List;

public class Rule {

  record BiddingResult(Bid bid, Rule triggeredRule) {}

  private int priority;
  private final Evaluation evaluation;
  private final List<Situation> situation;
  private final BiddingResult suggestedBid;
  private final String description;

  private Rule(int priority, String description, Evaluation evaluation, Bid bid, List<Situation> situation) {
    this.priority = priority;
    this.description = description;
    this.evaluation = evaluation;
    this.situation = situation;
    this.suggestedBid = new BiddingResult(bid, this);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private int priority;
    private String description;
    private Evaluation evaluation;
    private Bid bid;
    private List<Situation> situations;

    private Builder() {
      // Private constructor to enforce usage of Rule.builder()
    }

    public Builder priority(int priority) {
      this.priority = priority;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder evaluation(Evaluation evaluation) {
      this.evaluation = evaluation;
      return this;
    }

    public Builder suggestedBid(Bid bid) {
      this.bid = bid;
      return this;
    }

    public Builder situations(Situation... situations) {
      this.situations = Arrays.asList(situations);
      return this;
    }

    public Builder situations(List<Situation> situations) {
      this.situations = situations;
      return this;
    }

    public Rule build() {
      return new Rule(priority, description, evaluation, bid, situations);
    }
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

  public String getDescription() {
    return description;
  }
}
