package it.polimi.ingsw.pfIngSoft2020.Tower;

public interface Action {
	void movement(Level start,Level end);
	void buildcell(Level level);
	void newBuilder(Level level,String color);
	void killBuilder(Level dead);
}
