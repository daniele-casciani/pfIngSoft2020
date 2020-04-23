package tower;

public interface Level {
	int[] getPosition();
	//return 0=x 1=y
	int getHeight();
	//return 0=cell 1/2/3=tower 4=dome -1=builder
}
