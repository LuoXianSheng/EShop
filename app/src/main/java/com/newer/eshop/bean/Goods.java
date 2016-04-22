package com.newer.eshop.bean;

import java.util.List;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class Goods {

    //商品的类型
    String type;

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

    //商品的所有评论
    List<Conment> list;

    public Goods() {
    }

    public Goods(int id, String image_path, List<Conment> list, String name, float price, int sell, String type) {
        this.id = id;
        this.image_path = image_path;
        this.list = list;
        this.name = name;
        this.price = price;
        this.sell = sell;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<Conment> getList() {
        return list;
    }

    public void setList(List<Conment> list) {
        this.list = list;
    }
}
