package com.computerdude.epsilon.ranks;

public enum Rank {

	DEFAULT(0), ADMIN(5000), OWNER(9001);

	private final int level;

	Rank(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
