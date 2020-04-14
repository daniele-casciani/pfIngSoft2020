package tower;

final class Dome extends Floor {
	private  Level level;
	 Dome(Level level) {
		this.level=level;
	}
	 Level getLevel() {
		return level;
	}
}
