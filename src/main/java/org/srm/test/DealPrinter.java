package org.srm.test;

import org.srm.test.bridge.Deal;
import org.srm.test.bridge.Hand;
import org.srm.test.bridge.Player;
import org.srm.test.bridge.card.Card;
import org.srm.test.bridge.card.Suit;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DealPrinter {
    public static void main(String[] args) {
        //Deal d = new Deal().nextDeal();

        Deal d = Deal.parse( "N:AK.AQ.J9.AKT9642 T3.973.Q87632.Q8 Q764.KJT62.AT5.7 J9852.854.K4.J53");
        int pad = d.highestSuitLength() + 7;
        writeHand(pad, d, Player.NORTH);
        final String padding1 = IntStream.range(0, pad).mapToObj(i -> " ").collect(Collectors.joining());
        final String dashedLine = IntStream.range(0, pad).mapToObj(i -> "-").collect(Collectors.joining());
        System.out.println(padding1 + "."+dashedLine+".");

        writeHand(pad, d, Player.WEST, Player.EAST);
        System.out.println(padding1 + "."+dashedLine.replaceAll("-","_")+".");

        writeHand(pad, d, Player.SOUTH);

    }

    static void writeHand(int pad, Deal d, Player p) {
        final String padding = IntStream.range(0, pad+2).mapToObj(i -> " ").collect(Collectors.joining());
        System.out.println(padding + getStringRepresentation(Suit.SPADE, p, d));
        System.out.println(padding + getStringRepresentation(Suit.HEART, p, d));
        System.out.println(padding + getStringRepresentation(Suit.DIAMOND, p, d));
        System.out.println(padding + getStringRepresentation(Suit.CLUB, p, d));
    }

    static void writeHand(int pad, Deal d, Player p1, Player p2) {
        for(Suit suit:Suit.cardSuits())  printSuit(pad, d, p1, p2, suit);
    }

    private static void printSuit(int pad, Deal d, Player p1, Player p2, Suit suit) {
        String p1holding = getStringRepresentation(suit, p1, d);
        System.out.print(p1holding);
        final String padding1 = IntStream.range(0, pad -p1holding.length()).mapToObj(i -> " ").collect(Collectors.joining());
        System.out.print(padding1+"|");
        final String padding2 = IntStream.range(0, pad ).mapToObj(i -> " ").collect(Collectors.joining());
        System.out.print(padding2+"|   ");
        String p2Holding = getStringRepresentation(suit, p2, d);
        System.out.println(p2Holding);
    }


    static String getStringRepresentation(Suit suit, Player p, Deal d) {
        Hand h = d.getHand(p);
        final String holding = h.isVoid(suit)? "-" : h.holding(suit).stream().map(Card::rank).sorted().map(Objects::toString).collect(Collectors.joining());
        return suit.toString() + " " + holding;
    }
}
