package com.example.travelapplication;

public class Goal {
    private String id;
    private String userId;
    private String name;
    private String description;
    private boolean isDone;

    public Goal(String id, String userId, String name, String description, boolean isDone){
        setId(id);
        setUserId(userId);
        setName(name);
        setDescription(description);
        setDone(isDone);
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

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isDone() {
        return isDone;
    }
}
