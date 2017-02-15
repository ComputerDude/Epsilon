package com.epsilon.item;

public enum RequirementType {

    LEVEL("Level Req");

    private final String name;

    RequirementType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
