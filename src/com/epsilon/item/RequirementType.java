package com.epsilon.item;

import static com.epsilon.util.I18n.tl;

public enum RequirementType {

    LEVEL("item.requirement.level");

    private final String name;

    RequirementType(String name) {
        this.name = name;
    }

    public String getName() {
        return tl(name);
    }

}
