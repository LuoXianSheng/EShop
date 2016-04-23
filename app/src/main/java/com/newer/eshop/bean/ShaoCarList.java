package com.newer.eshop.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/23.
 */
public class ShaoCarList {

    //所有购物车里商品属性对象的集合
    private ArrayList<ShaoCar> list;

    public ShaoCarList(ArrayList<ShaoCar> list) {
        this.list = list;
    }

    public ArrayList<ShaoCar> getList() {
        return list;
    }

    public void setList(ArrayList<ShaoCar> list) {
        this.list = list;
    }
}
