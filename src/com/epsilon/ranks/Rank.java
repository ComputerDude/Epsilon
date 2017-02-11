package com.epsilon.ranks;

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
    

}
