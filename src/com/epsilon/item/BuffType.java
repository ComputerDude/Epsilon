package com.epsilon.item;

import java.util.Arrays;

import static com.epsilon.util.I18n.tl;

public enum BuffType {

    REGENERATION("%", "item.buff.regeneration"),
    STRENGTH("%", "item.buff.strength"),
    RESISTANCE("%", "item.buff.resistance"),
    SPEED("%", "item.buff.speed");

    private final String unit;
    private final String name;

    BuffType(final String unit, final String name) {
        this.name = name;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return tl(name);
    }

    public int getID() {
        return Arrays.binarySearch(values(), this);
    }

    public static BuffType fromID(int id) {
        return values()[id];
    }

}
