package me.knight.mars.entity;

import java.util.HashMap;
import java.util.Map;

public enum Orientation {
    NORTH("N") {
        @Override
        public Orientation getLeft() {
            return WEST;
        }

        @Override
        public Orientation getRight() {
            return EAST;
        }
    }, SOUTH("S") {
        @Override
        public Orientation getLeft() {
            return EAST;
        }

        @Override
        public Orientation getRight() {
            return WEST;
        }
    }, EAST("E") {
        @Override
        public Orientation getLeft() {
            return NORTH;
        }

        @Override
        public Orientation getRight() {
            return SOUTH;
        }
    }, WEST("W") {
        @Override
        public Orientation getLeft() {
            return SOUTH;
        }

        @Override
        public Orientation getRight() {
            return NORTH;
        }
    };

    private static final Map<String, Orientation> DIRECTION_TO_ORIENTATION_MAP = new HashMap<>();

    static {
        for (Orientation orientation : values()) {
            DIRECTION_TO_ORIENTATION_MAP.put(orientation.direction, orientation);
        }
    }

    private final String direction;

    Orientation(String n) {
        direction = n;
    }

    public String getDirection() {
        return direction;
    }

    public static Orientation getOrientationForDirection(String direction) {
        return DIRECTION_TO_ORIENTATION_MAP.get(direction);
    }

    public abstract Orientation getLeft();

    public abstract Orientation getRight();
}
