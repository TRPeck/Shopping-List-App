package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemDBHelper extends SQLiteAssetHelper {

    private static final String dbName = "itemdb.db";
    private static final int dbVersion = 1;

    private static String itemTable = "SHOPPINGLISTAPP";
    private static String _ID = "item_id";
    private static String nameColumn = "item";
    private static String aisleColumn = "aisle";

    public ItemDBHelper(Context context){
        super(context, dbName, null, dbVersion);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + itemTable);
        onCreate(db);
    }

    public boolean insert(String name, int aisle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(nameColumn, name);
        contentValues.put(aisleColumn, aisle);
        long result = db.insert(itemTable, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public ArrayList<Item> findAll(){
        ArrayList<Item> items = null;
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + itemTable, null);
            if(cursor.moveToFirst()){
                items = new ArrayList<Item>();
                do{
                    Item item = new Item();
                    item.setName(cursor.getString(1));
                    item.setAisle(1);
                    items.add(item);
                } while(cursor.moveToNext());
            }
        } catch(Exception e){
            items = null;
        }
        return items;
    }

    public ArrayList<Item> search(String keyword){
        ArrayList<Item> items = null;
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + itemTable + " WHERE " + nameColumn + " LIKE ?", new String[] {"%" + keyword + "%"});
            if(cursor.moveToFirst()){
                items = new ArrayList<Item>();
                do{
                    Item item = new Item();
                    item.setName(cursor.getString(1));
                    item.setAisle(1);
                    items.add(item);
                } while(cursor.moveToNext());
            }
        } catch(Exception e){
            items = null;
        }
        return items;
    }
}
