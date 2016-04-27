package com.newer.eshop.bean;

/**
 * Created by Mr_LUO on 2016/4/26.
 */
public class Cart {//购物车对象实体类

    private Goods goods;
    private int count;

    public Cart() {
    }

    public Cart(Goods goods, int count) {
        this.goods = goods;
        this.count = count;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
