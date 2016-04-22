package com.newer.eshop.bean;

/**
 * Created by Mr_LUO on 2016/4/22.
 */
public class GoodsClassify {

    private int id;//分类ID
    private String name;//类别名称
    private String imgPath;//类别小图片

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
