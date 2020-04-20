package Game;
import tower.*;

public class Map {
	private Cell[][] matrixCell = new Cell[5][5];

	 Cell[][] getMatrixCell() {
		return matrixCell;
	}
	 Cell getCell(int x, int y) {
		return matrixCell[x][y];
	}
	 Map() {
		for (int j=0; j<5; j++) {
			for (int i=0; i<5; i++) {
				matrixCell[i][j] = new Cell();
			}
		}
	}
	public void setCell(Level change) {
		
	}
	
}
