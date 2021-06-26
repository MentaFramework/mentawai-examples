package org.hellomenta;

import org.hellomenta.action.HelloAction;
import org.mentawai.core.ApplicationManager;

public class AppManager extends ApplicationManager {

	@Override
	public void loadActions() {
		
		action("/Hello", HelloAction.class, "hi")
			.on(SUCCESS, fwd("/jsp/hello.jsp"));
		
	}
	
}