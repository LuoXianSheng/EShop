package com.newer.eshop.bean;

import java.util.ArrayList;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class SubmitOrder {

    //订单类
    private int id;//订单ID
    private String phone;//用户手机号码
    private ArrayList<Goods> goodses;//商品ID集合，一条订单记录可以提交多件商品
    private ArrayList<Integer> count;//购买的数量
    private String date;//订单提交的时间
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Goods> getGoodses() {
        return goodses;
    }

    public void setGoodses(ArrayList<Goods> goodses) {
        this.goodses = goodses;
    }

    public ArrayList<Integer> getCount() {
        return count;
    }
    public void setCount(ArrayList<Integer> count) {
        this.count = count;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
