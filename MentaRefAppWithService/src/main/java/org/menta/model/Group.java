package org.menta.model;

public enum Group {

	GUEST(1), ADMIN(2), MASTER(3);
	
	private int code;

    private Group(int code) {
    	this.code = code;
    }

    public int getCode() {
    	return code;
    }
    
    public static Group fromCode(int code) {
    	for(Group g : Group.values()) {
    		if (g.getCode() == code) {
    			return g;
    		}
    	}
    	return null;
    }
}
