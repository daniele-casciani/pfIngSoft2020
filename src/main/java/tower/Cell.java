package tower;

public class Cell implements Level{
	int[] position ;
	
	public Cell(int x,int y) {
		this.position= new int[]{x,y};
	}
	
	public int[] getPosition() {
		return position;
	}
	Level getLevel() {
		return this;
	}
	public int getHeight() {
		return 0;
	}
}

