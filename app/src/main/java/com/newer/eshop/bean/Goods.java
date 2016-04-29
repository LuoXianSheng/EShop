package com.newer.eshop.bean;

import java.util.List;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class Goods {

    //商品的类型
    int typeId;

    //商品ID
    int id;

    //商品名称
    String name;

    //商品价格
    float price;

    //商品销售量
    int sell;

    //商品图片(返回图片的路径)
    String image_path;


    public Goods() {
    }

    public Goods(int id) {
        this.id = id;
    }

    public Goods(int typeId, int id, String name, float price, int sell, String image_path) {
        this.typeId = typeId;
        this.id = id;
        this.name = name;
        this.price = price;
        this.sell = sell;
        this.image_path = image_path;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

}
