package org.srm.test.bridge.bid.system;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.srm.test.bridge.Hand;
import org.srm.test.bridge.bid.Auction;
import org.srm.test.bridge.bid.Bid;
import org.srm.test.bridge.bid.system.Rule.BiddingResult;

public class Engine {
  private final List<Rule> masterRules;

  public Engine(BiddingSystem biddingSystem) {
    this.masterRules = new ArrayList<>(biddingSystem.getRules());
    // Ensure we always check high-priority (Conventions) first
    this.masterRules.sort(Comparator.comparingInt(Rule::getPriority));
  }

  public Bid suggestBid(Hand hand, Auction auction) {
    return masterRules.stream()
        .filter(rule -> rule.match(hand, auction))
        .findFirst() // Get the highest priority match
        .map(Rule::getSuggestedBid)
        .map(BiddingResult::bid)
        .orElse(Bid.PASS); // Default to Pass if no rules match
  }
}