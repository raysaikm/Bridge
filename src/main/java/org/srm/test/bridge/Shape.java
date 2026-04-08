package org.srm.test.bridge;

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
        shape = holdings.values().stream().map(Collection::size).sorted().toList();
    }
    public boolean isBalanced() {
        return shape.size() == 4 && // No void
                shape.stream().allMatch(i -> i>1) && // no singleton or void
                shape.stream().filter(i -> i==2).count() <= 1; // At most one doubleton

    }

    public boolean isUnBalanced() {
        return shape.size()  <4 || shape.stream().anyMatch(i -> i<=1); // contains singleton or void;
    }

    public Type getType() {
        if (isBalanced()) return Type.BALANCED;
        else if (isUnBalanced()) return Type.UNBALANCED;
        else return Type.SEMI_BALANCED;
    }

    public int lp() {
        return shape.stream().filter(i -> i>4).mapToInt(i -> i-4).sum();
    }
    public int highestSuitLength() { return shape.get(0);}

    @Override
    public String toString() { return getType() + "("
            + shape.stream().map(Object::toString).collect(Collectors.joining("-"))
            + ")";
    }
}
