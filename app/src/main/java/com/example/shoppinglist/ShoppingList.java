package com.example.shoppinglist;



public class ShoppingList  {
    private int id;
    private String list_name;
    private String created_at;

    public ShoppingList(){}

    public ShoppingList(String listName){
        this.list_name = listName;
    }

    public ShoppingList(String listName, int id){
        this.list_name = listName;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return list_name;
    }

    public void setListName(String listName) {
        this.list_name = listName;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
