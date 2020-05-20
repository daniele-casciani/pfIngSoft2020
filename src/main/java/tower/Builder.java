package tower;

final class Builder extends Floor {
	final private String name;
	
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
	 @Override
	 public int getHeight() {
		 return -1;
	 }
	 @Override
	 public Level getLevel() { 
		return this.level;
	 }
}