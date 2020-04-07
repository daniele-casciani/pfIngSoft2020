package it.polimi.ingsw.pfIngSoft2020.Tower;

final class Builder extends Floor {
	final private String color;
	private  Level level;
	protected Builder(Level level, String color) {
		this.level=level;
		this.color=color;
	}
	protected Level getLevel() {
		return level;
	}
	public String getColor() {
		return color;
	}
	protected Level move(Level level) {
		Level oldlevel=this.level;
		this.level=level;
		return oldlevel;
	}
}