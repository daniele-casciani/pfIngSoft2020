package Game;
import tower.*;

public class Map {
	private Cell[][] matrixCell = new Cell[5][5];

	public Cell[][] getMatrixCell() {
		return matrixCell;
	}

	public void setMatrixCell(Cell[][] matrixCell) {
		this.matrixCell = matrixCell;
	}
	public Map() {
		for (int j=0; j<5; j++) {
			for (int i=0; i<5; i++) {
				matrixCell[i][j] = new Cell();
			}
		}
	}
	
	public Cell getCell(int i, int j) {
		return matrixCell[i][j];
	}
}
