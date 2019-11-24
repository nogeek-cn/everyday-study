package com.darian;

public class User {
    private int id;
    private String uuId;

    public User(int id, String uuId) {
        this.id = id;
        this.uuId = uuId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }
}
