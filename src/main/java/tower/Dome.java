package tower;

final class Dome extends Floor {
	 
	Dome(Level level) {
		this.level=level;
	}
	 
	 @Override
	 public int getHeight() {
		 return 4;
	 }
}
