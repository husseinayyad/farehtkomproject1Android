package com.example.hussein.farhetkm.Model;

public class users {
    private String name, password, phone, image, address,usertype,state;

    public users() {

    }

    public users(String name, String password, String phone, String image, String address, String usertype, String state) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.image = image;
        this.address = address;
        this.usertype = usertype;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
