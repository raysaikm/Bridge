package org.srm.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.srm.test.bridge.Hand;
import org.srm.test.bridge.bid.Auction;
import org.srm.test.bridge.bid.Bid;
import org.srm.test.bridge.bid.Situation;
import org.srm.test.bridge.bid.system.Rule;
import org.srm.test.bridge.bid.system.StandardBiddingSystem;

class StandardBiddingSystemTest {

  private Rule opening1NT;
  private Rule opening1Spade;

  @BeforeEach
  void setUp() {
    // Priority 10: Jad's 1NT (15-17 balanced)
    opening1NT = Rule.builder()
        .priority(10)
        .description("1NT Opening")
        .evaluation(h -> h.hcp() >= 15 && h.hcp() <= 17 && h.shape().isBalanced())
        .suggestedBid(Bid._1NT)
        .situations(Situation.isOpeningBid())
        .build();

    // Priority 20: Jad's 1S (12+ points, 5+ spades)
    opening1Spade = Rule.builder()
        .priority(20)
        .description("1S Opening")
        .evaluation(h -> (h.hcp() + h.shape().lp()) >= 12 && h.spades().size() >= 5)
        .suggestedBid(Bid._1S)
        .situations(Situation.isOpeningBid())
        .build();
  }

  @ParameterizedTest(name = "Hand {0} should result in {1}")
  @MethodSource("provideCorrectedHandsFor1NT")
  void test1NTOpening(String handString, Bid expectedBid) {
    StandardBiddingSystem system = new StandardBiddingSystem("Jad 100", List.of(opening1NT));
    assertEquals(expectedBid, system.getNextBid(Hand.parse(handString), new Auction()));
  }

  private static Stream<Arguments> provideCorrectedHandsFor1NT() {
    return Stream.of(
        // 15 HCP: A(4), KQ(5), KJ(4), J(1) + (1) = 15. Balanced.
        Arguments.of("A9.KQ8.KJ5.J9432", Bid.PASS), // Wait, that's 5 clubs! Still balanced
        // Let's use a standard 4-3-3-3 shape:
        Arguments.of("AQJ.K76.T98.KJ32", Bid.PASS), // A(4)Q(2)J(1) + K(3) + 0 + K(3)J(1) = 14. Still 14!

        // LET'S GET THIS RIGHT:
        // 16 HCP: KJ(4), AQ(6), KQ(5), 2(0) + J(1) = 16
        Arguments.of("KJ3.AQ4.KQ5.J432", Bid._1NT),

        // 18 HCP: A(4)K(3) + A(4)Q(2) + K(3) + Q(2). (Too strong for 1NT, no 5-card major)
        // Note: In Jad 100, we haven't added 1C/1D "best minor" rules yet.
        Arguments.of("AK3.AQ4.K95.Q432", Bid.PASS)

    );
  }

  @Test
  @DisplayName("Priority Test: 1NT (15-17) wins over 1S (12+) even if 5 spades present")
  void testPriorityLogic() {
    // Hand: AKJ43 (8 HCP), QJ (3 HCP), K98 (3 HCP), J32 (1 HCP) = 15 HCP.
    // Shape is 5-2-3-3 (Balanced).
    Hand crossoverHand = Hand.parse("AKJ43.QJ.K98.J32");

    StandardBiddingSystem system = new StandardBiddingSystem("Priority Test",
        List.of(opening1Spade, opening1NT)
    );

    Bid result = system.getNextBid(crossoverHand, new Auction());
    assertEquals(Bid._1NT, result, "Should prefer 1NT at 15-17 over 1S");
  }

  @Test
  @DisplayName("Should open 1S with 11 HCP and 5 Spades (12 Total Points)")
  void testLengthPointsOpening() {
    // Hand: AKJT4 (8 HCP), 432 (0), QJ3 (3), 32 (0) = 11 HCP
    // Length Points: 5-card suit = 1 LP. Total = 12.
    Hand skinnyOpener = Hand.parse("AKJT4.432.QJ3.32");

    StandardBiddingSystem system = new StandardBiddingSystem("Test", List.of(opening1Spade));
    assertEquals(Bid._1S, system.getNextBid(skinnyOpener, new Auction()));
  }

  @Test
  @DisplayName("Should NOT open 1S with 10 HCP and 5 Spades (11 Total Points)")
  void testTooWeakEvenWithLP() {
    // Hand: KJT43 (4 HCP), 432 (0), QJ3 (3), K3 (3) = 10 HCP + 1 LP = 11.
    Hand weakHand = Hand.parse("KJT43.432.QJ3.K3");

    StandardBiddingSystem system = new StandardBiddingSystem("Test", List.of(opening1Spade));
    assertEquals(Bid.PASS, system.getNextBid(weakHand, new Auction()));
  }
}
