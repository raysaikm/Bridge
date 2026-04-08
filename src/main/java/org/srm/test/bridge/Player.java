package org.srm.test.bridge;

import java.util.Random;

public enum Player {
    NORTH, SOUTH, EAST, WEST;

    public static Player byInitial(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'N' -> NORTH;
            case 'E' -> EAST;
            case 'W' -> WEST;
            case 'S' -> SOUTH;

            default -> null;
        };
    }

    public static Player random() {
        if( new Random().nextBoolean() ) {
            if( new Random().nextBoolean() ) {
                return NORTH;
            }
            return SOUTH;
        } else {
            if( new Random().nextBoolean() ) {
                return EAST;
            }
            return WEST;
        }
    }
    private Hand hand;

    public Hand hand() {
        return hand;
    }

    public Player hand(Hand hand) {
        this.hand = hand;
        return this;
    }

    public Player lho() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST ;
            case WEST -> NORTH;
        };
    }

    public Player rho() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST ;
            case WEST -> SOUTH;
        };
    }

    public Player partner() {return lho().lho();}


    @Override
    public String toString() {
        return Character.toString(name().charAt(0));
    }
}
