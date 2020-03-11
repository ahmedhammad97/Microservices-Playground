package com.hammadltd.mainservice;

import java.util.UUID;

public class Info {
    private String name;
    private int age;
    private String description;
    private String id;

    public Info() { }

    public Info(String name, int age, String description) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.setUniqueId();
    }

    public void setUniqueId() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public String getSecretData() {
        return "";
    }
}
