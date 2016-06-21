package com.he.data.friendcircle;


import java.io.Serializable;

public class CommentItem implements Serializable {

	private String id;
	private Friend user;
	private Friend toReplyUser;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Friend getUser() {
		return user;
	}
	public void setUser(Friend user) {
		this.user = user;
	}
	public Friend getToReplyUser() {
		return toReplyUser;
	}
	public void setToReplyUser(Friend toReplyUser) {
		this.toReplyUser = toReplyUser;
	}
	
}
