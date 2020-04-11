package it.polimi.ingsw.pfIngSoft2020.Tower;

final class Builder extends Floor {
	final private String color;
	private  Level level;
	 Builder(Level level, String color) {
		this.level=level;
		this.color=color;
	}
	 Level getLevel() {
		return level;
	}
	public String getColor() {
		return color;
	}
	//prende la nuova casella e restituisce la vecchia
	 Level move(Level level) {
		Level oldlevel=this.level;
		this.level=level;
		return oldlevel;
	}
	 Level kill() {
		 return level;
	 }
}