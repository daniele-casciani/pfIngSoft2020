package it.polimi.ingsw.pfIngSoft2020.Tower;

final class Dome extends Floor {
	private  Level level;
	 Dome(Level level) {
		this.level=level;
	}
	 Level getLevel() {
		return level;
	}
}
