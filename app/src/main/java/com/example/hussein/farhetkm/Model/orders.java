package com.example.hussein.farhetkm.Model;

import com.google.firebase.database.IgnoreExtraProperties;


public class orders {


   private String  TotalAmount  , Address , city , name , phone ,date ,time  , state , oowner,image;

    public orders() {
    }

    public orders(String totalAmount, String address, String city, String name, String phone, String date, String time, String state, String oowner, String image) {
        TotalAmount = totalAmount;
        Address = address;
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.state = state;
        this.oowner = oowner;
        this.image = image;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOowner() {
        return oowner;
    }

    public void setOowner(String oowner) {
        this.oowner = oowner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
