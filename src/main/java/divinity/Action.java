package divinity;

import java.io.IOException;

import tower.Level;

interface Action {
	boolean execute(Object arg0,Object arg1) throws ClassCastException;
	Level[] request() throws IOException;		
}
