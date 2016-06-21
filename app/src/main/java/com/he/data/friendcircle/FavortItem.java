package com.he.data.friendcircle;

import java.io.Serializable;

public class FavortItem implements Serializable {


	private static final long serialVersionUID = 1L;
	private Friend user;
	public Friend getUser() {
		return user;
	}
	public void setUser(Friend user) {
		this.user = user;
	}
	
}
