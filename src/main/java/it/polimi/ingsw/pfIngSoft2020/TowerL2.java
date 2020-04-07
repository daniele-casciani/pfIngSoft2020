package it.polimi.ingsw.pfIngSoft2020;

final class TowerL2 extends Floor {
	private  TowerL1 tower;
	protected TowerL2(TowerL1 tower) {
		this.tower=tower;
	}
	protected TowerL1 getTower() {
		return tower;
	}
}
