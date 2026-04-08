package org.srm.test.bridge.bid.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.srm.test.bridge.Hand;
import org.srm.test.bridge.bid.Auction;
import org.srm.test.bridge.bid.Bid;

public class StandardBiddingSystem implements BiddingSystem {
    private final List<Rule> rules = new ArrayList<>();
    private final String systemName;

    public StandardBiddingSystem(String systemName, List<Rule> loadedRules) {
      this.systemName = systemName;
      this.rules.addAll(loadedRules);
      // Sort by priority so conventions (low numbers) are checked first
      this.rules.sort(Comparator.comparingInt(Rule::getPriority));
    }

  @Override
  public void addRule(Rule rule) {
    rules.add(rule);
    rules.sort(Comparator.comparingInt(Rule::getPriority));
  }

  public void addRules(Collection<Rule> newRules) {
    rules.addAll(newRules);
    rules.sort(Comparator.comparingInt(Rule::getPriority));
  }

  @Override
  public void addConvention(Convention convention) {
      addRules(convention.getRules());
  }

  @Override
  public List<Rule> getRules() {
    return rules;
  }

  @Override
  public Bid getNextBid(Hand hand, Auction auction) {
    return rules.stream()
        .filter(rule -> rule.match(hand, auction))
        .findFirst()
        .map(rule -> {
          // Optional: Log the rule description for debugging
          System.out.println("Matching Rule: " + rule.getDescription());
          return rule.getSuggestedBid().bid();
        })
        .orElse(Bid.PASS);
    }
  }