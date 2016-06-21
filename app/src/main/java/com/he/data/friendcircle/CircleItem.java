package com.he.data.friendcircle;

import android.text.TextUtils;

import java.util.List;


public class CircleItem  {

	public final static String TYPE_URL = "1";
	public final static String TYPE_IMG = "2";
	public final static String TYPE_VIDEO = "3";

	private String id;
	private String content;
	private String createTime;
	private String type;//1:链接  2:图片
	private String linkImg;
	private String linkTitle;
	private List<String> photos;
	private List<FavortItem> favorters;
	private List<CommentItem> comments;
	private Friend user;
	private Friend ower;
	private boolean isMyFavort ;

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

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<FavortItem> getFavorters() {
		return favorters;
	}
	public void setFavorters(List<FavortItem> favorters) {
		this.favorters = favorters;
	}

	public List<CommentItem> getComments() {
		return comments;
	}
	public void setComments(List<CommentItem> comments) {
		this.comments = comments;
	}

	public String getLinkImg() {
		return linkImg;
	}
	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	}

	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public List<String> getPhotos() {
		return photos;
	}
	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	public Friend getUser() {
		return user;
	}
	public void setUser(Friend user) {
		this.user = user;
	}

	public Friend getOwer() {
		return ower;
	}
	public void setOwer(Friend ower) {
		this.ower = ower;
	}

	public boolean hasFavort(){
		if(favorters!=null && favorters.size()>0){
			return true;
		}
		return false;
	}
	
	public boolean hasComment(){
		if(comments!=null && comments.size()>0){
			return true;
		}
		return false;
	}

	public boolean getIsMyFavort(){
		return this.isMyFavort;
	}

	public void setIsMyFavort(Boolean isMyFavort){
		this.isMyFavort = isMyFavort;
	}

	public String getMyFavortUserId() {
		String favortid = "";
		if (!TextUtils.isEmpty(ower.getUserId()) && hasFavort()) {
			for (FavortItem item : getFavorters()) {
				if (ower.getUserId().equals(item.getUser().getUserId())) {
					favortid = item.getUser().getUserId();
					setIsMyFavort(true);
					return favortid;
				}
			}
		}
		if(favortid.equals("")){
			setIsMyFavort(false);
		}
		return favortid;
	}
}
