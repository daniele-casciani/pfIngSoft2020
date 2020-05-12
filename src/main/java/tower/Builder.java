package tower;

final class Builder extends Floor {
	final private String name;
	private  Level level;
	 Builder(Level level, String name) {
		this.level=level;
		this.name=name;
	}
	public String getName() {
		return name;
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
	 public int getHeight() {
		 return -1;
	 }
	 
	 public int getDownLevelHeight() {
		return this.level.getHeight();
	 }
}