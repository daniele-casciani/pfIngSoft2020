package tower;

public interface Action {
	void movement(Level start,Level end);
	void buildTower(Level level);
	void buildDome(Level level);
	void newBuilder(Level level,String color);
	void killBuilder(Level dead);
	String builderName(Builder builder);
}
