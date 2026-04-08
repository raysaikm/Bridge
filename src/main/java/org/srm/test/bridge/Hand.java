package org.srm.test.bridge;

import org.srm.test.bridge.card.Card;
import org.srm.test.bridge.card.Rank;
import org.srm.test.bridge.card.Suit;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Hand {
    private final Map<Suit, Collection<Card>> holdings;

    Shape shape = new Shape();

    public Hand() {
        holdings = new HashMap<>();
    }

    public Hand(Set<Card> cards) {
        this.holdings = cards.stream().collect(Collectors.groupingBy(Card::suit, Collectors.toCollection(HashSet::new )));
        for(Suit s: Suit.cardSuits()) {
            holdings.putIfAbsent(s, Collections.emptySet());
        }
        shape.calculate(this.holdings);
    }


    public void dealTo(Card card) {
        final Collection<Card> suitHolding = this.holdings.getOrDefault(card.suit(), new HashSet<>());
        suitHolding.add(card);
        holdings.put(card.suit(), suitHolding);

        shape.calculate(this.holdings);
    }

    public Collection<Card> holding(Suit suit) {
        return holdings.getOrDefault(suit, new HashSet<>());
    }

    public Collection<Card> spades() { return holding(Suit.SPADE);}
    public Collection<Card> hearts() { return holding(Suit.HEART);}
    public Collection<Card> diamonds() { return holding(Suit.DIAMOND);}
    public Collection<Card> clubs() { return holding(Suit.CLUB);}

    public boolean isVoid(Suit suit) {
        return holding(suit).isEmpty();
    }

    public static Hand parse(String s) {
        final Set<Card> cards = new HashSet<>();
        Suit suit = Suit.SPADE;
        for(char c: s.toCharArray()) {
            if( c == '.') {
                suit = suit.lower();
            } else  if( suit == null ) {
                break;
            } else {
                cards.add(Card.card(suit,  Rank.get(c)));
            }
        }
        return new Hand(cards);
    }

    public int hcp() {
        return holdings.values().stream().flatMap(Collection::stream).mapToInt(Card::hcp).sum();
    }
    public int lp() {
        return shape.lp();
    }

    public int totalPoints() {
        return hcp() + lp();
    }

    public int sp(Suit trump) {
        return holdings.get(trump).size() >= 3? // must hold three trumps for shorts to count
                Suit.cardSuits().stream()
                        .filter(Predicate.not(trump::equals))
                        .map(s -> holdings.getOrDefault(s, Collections.emptyList()))
                        .mapToInt(l -> l.size())
                        .map(cardsInSuit -> switch (cardsInSuit) {
                            case 0 -> 5;
                            case 1 -> 3;
                            case 2 -> 1;
                            default -> 0;
                        })
                        .sum()
                :0;
    }

    public Shape shape() { return shape;}

    @Override
    public String toString() {
        return spades().stream().map(Card::rank).sorted().map(Object::toString).collect(Collectors.joining()) + "." +
                hearts().stream().map(Card::rank).sorted().map(Object::toString).collect(Collectors.joining()) + "." +
                diamonds().stream().map(Card::rank).sorted().map(Object::toString).collect(Collectors.joining()) + "." +
                clubs().stream().map(Card::rank).sorted().map(Object::toString).collect(Collectors.joining());
    }

    public int highestSuitLength() { return shape().highestSuitLength();}
}
