package it.polimi.ingsw.pfIngSoft2020;

final class Dome extends Floor {
	private  Level level;
	protected Dome(Level level) {
		this.level=level;
	}
	protected Level getLevel() {
		return level;
	}
}
