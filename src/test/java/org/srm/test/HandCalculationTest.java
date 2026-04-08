package org.srm.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.srm.test.bridge.Hand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandCalculationTest {

  @Test
  @DisplayName("HCP Calculation: AKQJ in one suit + minor honors")
  void testHcpCalculation() {
    // AKQJ(10) + K(3) + Q(2) + J(1) = 16
    Hand hand = Hand.parse("AKQJ.K54.Q54.J43");
    assertEquals(16, hand.hcp());
  }

  @Test
  @DisplayName("Shape and LP: 5-4-3-1 (Unbalanced)")
  void testUnbalancedShape() {
    Hand hand = Hand.parse("AKQJ2.K542.Q54.J");

    assertTrue(hand.shape().isUnBalanced());
    assertFalse(hand.shape().isBalanced());
    assertEquals(5, hand.shape().highestSuitLength());

    // 5-card suit = 1 LP. No other suit > 4.
    assertEquals(1, hand.shape().lp());
    assertEquals(17, hand.totalPoints()); // 16 HCP + 1 LP
  }

  @Test
  @DisplayName("Shape: 4-4-3-2 (Balanced)")
  void testBalancedShape() {
    Hand hand = Hand.parse("AKJ2.KQ42.Q54.J3");

    assertTrue(hand.shape().isBalanced());
    assertEquals(4, hand.shape().highestSuitLength());
    assertEquals(0, hand.shape().lp());
  }

  @Test
  @DisplayName("Void and Singletons: Extreme Unbalanced")
  void testVoids() {
    // 7-6-0-0 shape
    Hand extreme = Hand.parse("AKQJT98.KQJT98..");

    assertTrue(extreme.shape().isUnBalanced());
    assertEquals(7, extreme.shape().highestSuitLength());
    // (7-4) + (6-4) = 3 + 2 = 5 LP
    assertEquals(5, extreme.shape().lp());
  }
}
