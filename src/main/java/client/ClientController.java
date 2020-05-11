package client;

public interface ClientController extends Runnable{
	
	void setText(String string);

	void addConstructor(int x, int y);

	void construction(int x, int y, int z);
}
