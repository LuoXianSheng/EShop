package com.newer.eshop.bean;

/**
 * Created by Mr_LUO on 2016/4/27.
 */
public class Address {

    private int id;
    private String name;
    private String phone;
    private String address;
    private int type;


    public Address() {
        super();
    }

    public Address(int id, String name, String phone, String address, int type) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
