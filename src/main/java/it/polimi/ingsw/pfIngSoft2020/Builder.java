package it.polimi.ingsw.pfIngSoft2020;

final class Builder extends Floor {
	private  Level level;
	protected Builder(Level level) {
		this.level=level;
	}
	protected Level getLevel() {
		return level;
	}
}