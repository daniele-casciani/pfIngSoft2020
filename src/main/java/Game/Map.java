package Game;
import it.polimi.ingsw.pfIngSoft2020.Tower.*;

public class Map {
	private Cell[] matrixCell = new Cell[25];
	
	public Map() {
		for (int i=0; i<25; i++) {
			matrixCell[i] = new Cell();
		}
	}
}
