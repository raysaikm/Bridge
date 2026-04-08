package org.srm.test.bridge.bid.system;

import java.util.Collection;
import java.util.List;

public interface BiddingSystem {

  List<Rule> getRules();

  void addRule(Rule rule);

  void addConvention(Convention convention);
}
