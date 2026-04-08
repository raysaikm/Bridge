package org.srm.test.bridge.card;

import java.util.Collection;
import java.util.List;

public class Suit implements Comparable<Suit> {
    public static final Suit NOTRUMP = new Suit("NOTRUMP", "NT");
    public static final Suit SPADE = new Suit("SPADE", "\u2660");
    public static final Suit HEART = new Suit("HEART", "\u2665");
    public static final Suit DIAMOND = new Suit("DIAMOND", "\u2666");
    public static final Suit CLUB = new Suit("CLUB","\u2663");

    private final String representation;

    public String name() {
        return name;
    }

    private final String name;
    private Suit(String name, final String representation) {
        this.name=name;
        this.representation = representation;
    }

    public static Suit get(char c) {
        return switch (Character.toLowerCase(c)) {
            case 's' -> SPADE;
            case 'h' -> HEART;
            case 'd' -> DIAMOND;
            case 'c' -> CLUB;
            default -> null;
        };
    }

    public Suit higher() {

        if( this ==  SPADE ) return NOTRUMP;
        if( this ==  HEART ) return SPADE;
        if( this ==  DIAMOND ) return HEART;
        if( this ==  CLUB ) return DIAMOND;

        return null;
    }

    public Suit lower() {
        if( this ==  NOTRUMP ) return SPADE;
        if( this ==  SPADE ) return HEART;
        if( this ==  HEART ) return DIAMOND;
        if( this ==  DIAMOND ) return CLUB;

        return null;
    }

    public static Collection<Suit> cardSuits() {
        return List.of(SPADE, HEART, DIAMOND, CLUB);
    }

    public static Collection<Suit> bidSuits() {
        return List.of(NOTRUMP, SPADE, HEART, DIAMOND, CLUB);
    }


    @Override
    public String toString() {
        return representation;
    }

    @Override
    public int compareTo(Suit that) {
        if (this == that) return 0;
        else if( this == NOTRUMP ) return 1;
        else if( this == SPADE) return that == NOTRUMP ?-1:1;
        else if( this == HEART) {
            if( that == SPADE || that == NOTRUMP) return -1;
            if( that == DIAMOND || that == CLUB) return 1;
        }
        else if( this == DIAMOND) return that == CLUB ?1:-1;
        else if( this == CLUB ) return -1;

        return 0;
    }

}