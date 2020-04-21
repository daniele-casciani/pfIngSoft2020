package Game;
import tower.*;

public class Map {
	private Level[][] matrixCell = new Level[5][5];

	 Level[][] getMatrixCell() {
		return matrixCell;
	}
	 public Level getCell(int x, int y) {
		return matrixCell[x][y];
	}
	 Map() {
		for (int j=0; j<5; j++) {
			for (int i=0; i<5; i++) {
				matrixCell[i][j] = new Cell(i,j);
			}
		}
	}
	public void setCell(Level change) {
		matrixCell[change.getPosition()[0]][change.getPosition()[1]]=change;
		
	}
	
}
