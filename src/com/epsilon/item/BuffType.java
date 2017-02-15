package com.epsilon.item;

public enum BuffType {

    REGENERATION("%", "Regeneration"), STRENGTH("%", "Strength"), RESISTANCE("%", "Resistance"), SPEED("%", "Speed");

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
    
}
