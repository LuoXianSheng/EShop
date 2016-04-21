package com.newer.eshop.bean;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class Conment {

    private int id;//评论id
    private int userId;//评论者ID
    private String content;
    private String imgPath;//评论的图片集合
    private String time;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public String getDate() {
        return time;
    }
    public void setDate(String date) {
        this.time = date;
    }
}
