package tower;

final class Builder extends Floor {
	final private String color;
	private  Level level;
	 Builder(Level level, String color) {
		this.level=level;
		this.color=color;
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