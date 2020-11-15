package com.example.shoppinglist;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String name;
    private int aisle;
    private String created_at;

    public Item() {
    }

    public Item(String name, int aisle){
        this.name = name;
        this.aisle = aisle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAisle() {
        return aisle;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
