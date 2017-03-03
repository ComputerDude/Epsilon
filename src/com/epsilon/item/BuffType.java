package com.epsilon.item;

import java.util.Arrays;

public enum BuffType {

    REGENERATION("%", "Regeneration"), STRENGTH("%", "Strength"), RESISTANCE("%", "Resistance"), SPEED
            ("%", "Speed");

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
        return name;
    }

    public int getID() {
        return Arrays.binarySearch(values(), this);
    }

    public static BuffType fromID(int id) {
        return values()[id];
    }

}
