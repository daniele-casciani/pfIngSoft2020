package divinity;

import java.io.IOException;

interface Action {
	boolean execute(Object arg0,Object arg1) throws ClassCastException;
	Object[] request() throws IOException;		
}
