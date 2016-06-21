package com.he.data.friendcircle;

import java.io.Serializable;
import java.util.List;

public class OpenImageData implements Serializable {
    //    是否匿名，0不匿名，1匿名
    public int anonymous;
    //    评论图片列表
    public List<CommentPicBean> comments;
    public PhotoBean user;

    public String content;
    public String creatTime;
    public int commentId;
    public int grade;
    public int sid;
    public String orderId;
    public String userName;
    //回复列表内容
    // public List<CommentReplysBean> commentsReplys;

    @Override
    public String toString() {
        return "CommentPicBean{" +
                "anonymous=" + anonymous +
                ", comments=" + comments +
                ", content='" + content + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", commentId=" + commentId +
                ", grade=" + grade +
                ", sid=" + sid +
                ", orderId='" + orderId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    /** comment*/
    public class CommentPicBean implements Serializable {
        public int height;
        public int width;
        public int x;
        public int y;
        public int commentId;
        public String imageId;
        /**原图*/
        public String imageUrl;
        /**缩略图*/
        public String smallImageUrl;
        @Override
        public String toString() {
            return "CommentPicBean{" +
                    "commentId=" + commentId +
                    ", imageId='" + imageId + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", smallImageUrl='" + smallImageUrl + '\'' +
                    '}';
        }
    }

    /** photo */
    public class PhotoBean implements Serializable {
        public String photoId;
        public String picUrl;
        public String smallPicUrl;

        @Override
        public String toString() {
            return "PhotoBean{" +
                    "photoId='" + photoId + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", smallPicUrl='" + smallPicUrl + '\'' +
                    '}';
        }
    }

}

