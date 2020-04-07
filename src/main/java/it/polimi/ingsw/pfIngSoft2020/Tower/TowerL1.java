package it.polimi.ingsw.pfIngSoft2020.Tower;

final class TowerL1 extends Floor {
	private  TowerL0 tower;
	protected TowerL1(TowerL0 tower) {
		this.tower=tower;
	}
	protected TowerL0 getTower() {
		return tower;
	}
}
