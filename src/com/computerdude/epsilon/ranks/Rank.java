package com.computerdude.epsilon.ranks;

public enum Rank {

	DEFAULT(0);

	private final int level;

	Rank(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
