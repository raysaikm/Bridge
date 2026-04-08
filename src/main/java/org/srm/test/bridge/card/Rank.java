package org.srm.test.bridge.card;

import java.util.Arrays;
import java.util.Collection;

public enum Rank  implements Comparable<Rank> {
    ACE('A', 4), KING('K', 3), QUEEN('Q', 2), JACK('J', 1), TEN('T'),
    NINE('9'), EIGHT('8'), SEVEN('7'), SIX('6'), FIVE('5'),
    FOUR('4'), THREE('3'), TWO('2'), SPOT('x');

    public static Rank get(char c) {
        return switch (Character.toLowerCase(c)) {
            case 'a' -> ACE;
            case 'k' -> KING;
            case 'q' -> QUEEN;
            case 'j' -> JACK;
            case 't' -> TEN;
            case '9' -> NINE;
            case '8' -> EIGHT;
            case '7' -> SEVEN;
            case '6' -> SIX;
            case '5' -> FIVE;
            case '4' -> FOUR;
            case '3' -> THREE;
            case '2' -> TWO;
            default -> SPOT;
        };
    }

    public static Collection<Rank> all() {
        return Arrays.stream(values()).filter(r -> r!=SPOT).toList();
    }

    int hcp;
    char representation;
    Rank(final char representation, final int hcp) {this.representation = representation; this.hcp = hcp; }
    Rank(final char representation ) {this(representation, 0);}

    public int hcp() {
        return hcp;
    }

    @Override
    public String toString() {
        return Character.toString(representation);
    }
}