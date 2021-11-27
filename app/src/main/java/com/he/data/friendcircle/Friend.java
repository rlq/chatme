package com.he.data.friendcircle;


import com.bumptech.glide.load.engine.Resource;

public class Friend   {

    private String userId;
    private String nickname;
    private String nicknamePinyin;
    private String portrait;
    private char searchKey;
    private Resource portraitResource;
    private boolean isSelected = false;
    private boolean isAdd = false;
    private String chatContent = "";

    public Friend(){

    }

    public Friend(String userId, String nickname, String portrait) {
        this.userId = userId;
        this.nickname = nickname;
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return  "id = " + userId
                + "; name = " + nickname
                + "; headUrl = " + portrait;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNicknamePinyin() {
        return nicknamePinyin;
    }

    public void setNicknamePinyin(String nicknamePinyin) {
        this.nicknamePinyin = nicknamePinyin;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public char getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(char searchKey) {
        this.searchKey = searchKey;
    }

    public Resource getPortraitResource() {
        return portraitResource;
    }

    public void setPortraitResource(Resource portraitResource) {
        this.portraitResource = portraitResource;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Friend friend = (Friend) obj;
        if ((this.getUserId() == friend.getUserId()) && (this.getNickname() == friend.getNickname()) && (this.getPortrait() == friend.getPortrait())) {
            return true;
        }
        return false;
    }
}
