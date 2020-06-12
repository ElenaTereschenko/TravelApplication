package com.example.travelapplication;

public class Good {
    private String id;
    private String userId;
    private String name;
    private String description;
    private int count;
    private boolean isTook;

    public Good(String id, String userId, String name, String description, int count, boolean isTook){
        setId(id);
        setUserId(userId);
        setName(name);
        setDescription(description);
        setCount(count);
        setTook(isTook);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTook(boolean took) {
        isTook = took;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isTook() {
        return isTook;
    }
}
