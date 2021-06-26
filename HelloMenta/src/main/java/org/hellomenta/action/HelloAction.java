package org.hellomenta.action;

import org.mentawai.core.BaseAction;

public class HelloAction extends BaseAction {
	
	public String hi() {
		String msg = input.getString("msg");
		if (isEmpty(msg)) {
			msg = "Why you did not type anything?";
		}
		output.setValue("msg", msg);
		return SUCCESS;
	}
}