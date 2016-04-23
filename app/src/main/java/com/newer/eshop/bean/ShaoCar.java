package com.newer.eshop.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/23.
 */
public class ShaoCar {
    //价格
    private String price;

    //商品图片
    private String image_path;

    //商品名字
    private String name;

    //商品ID
    private int shapid;

    public ShaoCar() {

    }

    public ShaoCar(String image_path, String name, String price, int shapid) {
        this.image_path = image_path;
        this.name = name;
        this.price = price;
        this.shapid = shapid;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getShapid() {
        return shapid;
    }

    public void setShapid(int shapid) {
        this.shapid = shapid;
    }
}
