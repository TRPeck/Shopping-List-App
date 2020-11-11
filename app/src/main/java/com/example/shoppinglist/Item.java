package com.example.shoppinglist;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private int aisle;

    public Item() {
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
}
