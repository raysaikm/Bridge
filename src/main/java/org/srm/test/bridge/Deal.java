package org.srm.test.bridge;

import org.srm.test.bridge.card.Card;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deal {
    private final Player dealer;

    public Deal(){
        this(null, null);
    }

    public Deal(Player dealer, Map<Player, Hand> hands) {
        this.dealer = dealer;
        if(hands != null) {
            this.dealer.hand(hands.get(dealer));
            this.dealer.lho().hand(hands.get(dealer.lho()));
            this.dealer.partner().hand(hands.get(dealer.partner()));
            this.dealer.rho().hand(hands.get(dealer.rho()));
        }
    }

    public Player getDealer() {
        return dealer;
    }

    public Deal nextDeal() {
        final Player newDealer =  (dealer == null)? Player.random():dealer.lho();

        Map<Player, Hand> hands = new HashMap<>();
        Player next = newDealer;
        for(Card c : Card.shuffle() ) {
            Hand h = hands.getOrDefault(next, new Hand());
            h.dealTo(c);
            hands.putIfAbsent(next, h);

            next = next.lho();
        }

        return new Deal(newDealer , hands);
    }

    public static Deal parse(String s) {
        final int colon = s.indexOf(':');
        final Player dealer = Player.byInitial(s.substring(0, colon).charAt(0));
        final List<Hand> hands = Arrays.stream(s.substring(colon+1).split(" ")).map(Hand::parse).toList();

        return new Deal( dealer, Map.of(dealer, hands.get(0),
                dealer.lho(), hands.get(1),
                dealer.partner(), hands.get(2),
                dealer.rho(), hands.get(3)));
    }

    public Hand getHand(Player p) {
        if(p == dealer) return dealer.hand();
        else if( p== dealer.lho()) return dealer.lho().hand();
        else if( p== dealer.partner()) return dealer.partner().hand();
        else return dealer.rho().hand();
    }

    public int highestSuitLength() {
        return Math.max(dealer.hand().highestSuitLength(),
                Math.max(dealer.lho().hand().highestSuitLength(),
                        Math.max(dealer.partner().hand().highestSuitLength(),
                                dealer.rho().hand().highestSuitLength())));
    }

    @Override
    public String toString() {
        return String.format("[Deal \"%s:%s %s %s %s\"]", dealer,
                dealer.hand(),
                dealer.lho().hand(),
                dealer.partner().hand(),
                dealer.rho().hand()) ;
    }
}
