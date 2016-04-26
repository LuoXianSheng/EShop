package com.newer.eshop.bean;

/**
 * Created by Mr_LUO on 2016/4/26.
 */
public class CBox {

    private int position;
    private boolean isCheck;

    public CBox() {
    }

    public CBox(int position, boolean isCheck) {
        this.position = position;
        this.isCheck = isCheck;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
