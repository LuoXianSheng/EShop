package com.newer.eshop.bean;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class Goods {
    //商品名称
    String name;

    //商品图片(返回图片的路径)
    String image_path;

    //商品价格
    float price;

    //商品快递费
    float express;

    //商品销售量
    double sell;

    //商品地址
    String path;

    public Goods(String name,String image_path, float price, float express
                        ,double sell,String path){
        this.name=name;
        this.image_path=image_path;
        this.price=price;
        this.express=express;
        this.sell=sell;
        this.path=path;
    }

    public float getExpress() {
        return express;
    }

    public void setExpress(float express) {
        this.express = express;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "express=" + express +
                ", name='" + name + '\'' +
                ", image_path='" + image_path + '\'' +
                ", price=" + price +
                ", sell=" + sell +
                ", path='" + path + '\'' +
                '}';
    }
}
