package com.newer.eshop.bean;

import java.util.ArrayList;

/**
 * Created by Mr_LUO on 2016/4/29.
 */
public class Order {

    public static final int ITEM = 1;
    public static final int SECTION = 0;

    private int type;//0是标题，1是内容

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int id;// 订单ID
    private String date;// 订单提交的时间
    private String phone;// 用户手机号码
    private int goodsid;
    private String name;
    private float price;
    private int count;
    private String image_path;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getGoodsid() {
        return goodsid;
    }
    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getImage_path() {
        return image_path;
    }
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
