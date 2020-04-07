package it.polimi.ingsw.pfIngSoft2020.Tower;

final class TowerL0 extends Floor {
	private  Cell cell;
	protected TowerL0(Cell cell) {
		this.cell=cell;
	}
	protected Cell getCell() {
		return cell;
	}
}
