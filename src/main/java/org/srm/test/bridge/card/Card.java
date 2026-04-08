package org.srm.test.bridge.card;

import java.util.*;

public class Card {
    static final Map<Suit, Map<Rank, Card>> deck = new HashMap<>();

    static {
        for(Suit s: Suit.cardSuits()) {
            Map<Rank, Card> index = new HashMap<>();
            for(Rank r : Rank.all()) {
                index.putIfAbsent(r, new Card(s, r));
            }
            deck.put(s, index);
        }
    }
    private final Suit suit;
    private final Rank rank;

    private Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit suit() {
        return suit;
    }

    public static Card card(Suit suit, Rank rank) {
        return deck.get(suit).get(rank);
    }

    public Rank rank() {
        return rank;
    }

    public int hcp() { return this.rank().hcp();}

    @Override
    public String toString() {
        return suit().toString() + rank().toString();
    }

    public static List<Card> shuffle() {
        final List<Card> cards = new ArrayList<>(deck.values().stream().flatMap(e -> e.values().stream()).toList());
        Collections.shuffle(cards);
        return cards;
    }
}
