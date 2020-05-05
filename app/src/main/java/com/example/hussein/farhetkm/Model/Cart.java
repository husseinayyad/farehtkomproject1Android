package com.example.hussein.farhetkm.Model;

public class Cart {
    public String pid, name, price, type, discount , date , time ,owner,im ;


    public  Cart(){

    }

    public Cart(String pid, String name, String price, String type, String discount, String date, String time, String owner, String im) {
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.type = type;
        this.discount = discount;
        this.date = date;
        this.time = time;
        this.owner = owner;
        this.im = im;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }
}
