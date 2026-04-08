package org.srm.test.bridge;

import java.util.Comparator;
import org.srm.test.bridge.card.Card;
import org.srm.test.bridge.card.Suit;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Shape {
    enum Type{BALANCED, SEMI_BALANCED, UNBALANCED}

    private List<Integer> shape;
    public void calculate(final Map<Suit, Collection<Card>> holdings) {
        // Sort descending (e.g., 5, 4, 2, 2) so index 0 is always the longest suit
        this.shape = holdings.values().stream()
            .map(Collection::size)
            .sorted(Comparator.reverseOrder())
            .toList();
    }

    public boolean isBalanced() {
        // Standard: No void(0), No singleton(1), At most one doubleton(2)
        // Common shapes: 4-3-3-3, 4-4-3-2, 5-3-3-2
        return shape.size() == 4 &&
            shape.get(3) > 1 &&
            shape.stream().filter(i -> i == 2).count() <= 1;
    }

    public boolean isUnBalanced() {
        // Standard: Has a singleton or void, or is extremely skewed (like two doubletons)
        return shape.size() < 4 || shape.stream().anyMatch(i -> i <= 1);
    }

    public int highestSuitLength() {
        return shape.isEmpty() ? 0 : shape.getFirst();
    }

    public Type getType() {
        if (isBalanced()) return Type.BALANCED;
        else if (isUnBalanced()) return Type.UNBALANCED;
        else return Type.SEMI_BALANCED;
    }

    public int lp() {
        return shape.stream().filter(i -> i>4).mapToInt(i -> i-4).sum();
    }

    @Override
    public String toString() { return getType() + "("
            + shape.stream().map(Object::toString).collect(Collectors.joining("-"))
            + ")";
    }
}
