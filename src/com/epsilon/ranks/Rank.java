package com.epsilon.ranks;

import static com.epsilon.util.I18n.tlc;

public enum Rank {

	//Staff ranks have a 100 Gap. If we ever add a rank between others, add 50, and 25 after that.
	
	DEFAULT(0), TRIAL(8500), MODERATOR(8600), ART(8700), DEV(8800), ADMIN(8900), OWNER(9001);

	private final int level;

	Rank(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static Rank fromLevel(int level) {
	    for (Rank rank : values()) {
	        if (rank.level == level) return rank;
        }
        return null;
    }

    public String getName() {
	    return tlc("player.rank." + name().toLowerCase()); // TODO Make this a constructor parameter
    }

}
