package com.epsilon.item;

public enum BuffType {

    REGENERATION("%", "Regeneration", 'r'), STRENGTH("%", "Strength", 's'), RESISTANCE("%", "Resistance", 'R'), SPEED
            ("%", "Speed", 'S');

    private final String unit;
    private final String name;
    private final char id;

    BuffType(final String unit, final String name, char id) {
        this.name = name;
        this.unit = unit;
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    public char getID() {
        return id;
    }

    public static BuffType fromID(char id) {
        for (BuffType type : values()) {
            if (type.id == id) return type;
        }
        return null;
    }

}
