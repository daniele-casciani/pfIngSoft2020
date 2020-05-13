package tower;

public interface GenericAction {
	void movement(Level start,Level end);
	void buildTower(Level level);
	void buildDome(Level level);
	void newBuilder(Level level,String color);
	void killBuilder(Level dead);
	String builderName(Level builder);
	int getHeight(Level level);//return 0=cell 1/2/3=tower 4=dome -1=builder
	Level getLUnderB(Level level); // return the level under the builder
	
}
