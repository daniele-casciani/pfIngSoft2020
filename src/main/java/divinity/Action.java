package divinity;

import java.io.IOException;

interface Action {
	boolean execute(Object arg0,Object arg1);
	Object[] request() throws IOException;		
}
