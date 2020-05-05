package com.example.hussein.farhetkm.Model;

public class Products {
    String  description;
    String image;
    String category;
    String price;
    String data;
    String time;
    String ID;
    String type;
    String proowner;
    String subcategory;

    public Products() {
    }

    public Products(String description, String image, String category, String price, String data, String time, String ID, String type, String proowner, String subcategory) {
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
        this.data = data;
        this.time = time;
        this.ID = ID;
        this.type = type;
        this.proowner = proowner;
        this.subcategory = subcategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProowner() {
        return proowner;
    }

    public void setProowner(String proowner) {
        this.proowner = proowner;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
