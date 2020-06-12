package com.example.travelapplication;

public class Purchase {
    private String id;
    private String userId;
    private String categoryId;
    private String name;
    private String description;
    private String cost;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;
    private boolean isBought;

    public Purchase(String id, String userId, String categoryId, String name, String description, boolean isBought, double price) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.isBought = isBought;
        this.price = price;
    }

    public Purchase(String name, String cost, String category){
        this.name = name;
        this.cost = cost;
        this.category = category;
    }

    private double price;

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }



    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBought() {
        return isBought;
    }


}
