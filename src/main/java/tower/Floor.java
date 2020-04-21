package tower;

class Floor implements Level{
	Level level;
	public int[] getPosition() {
		return level.getPosition();
	}
	Level getLevel() {
		return level;
	}
}
