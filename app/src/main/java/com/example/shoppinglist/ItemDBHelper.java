package com.example.shoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemDBHelper extends SQLiteOpenHelper {

    private static String dbName = "";
    private static int dbVersion = 1;

    private static String itemTable = "item";

    private static String nameColumn = "name";
    private static String aisleColumn = "aisle";

    public ItemDBHelper(Context context){
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " +
                itemTable + " (" +
                nameColumn + " TEXT NOT NULL, " +
                aisleColumn + " INTEGER NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + itemTable);
        onCreate(db);
    }

    public List<Item> findAll(){
        List<Item> items = null;
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + itemTable, null);
            if(cursor.moveToFirst()){
                items = new ArrayList<Item>();
                do{
                    Item item = new Item();
                    item.setName(cursor.getString(0));
                    item.setAisle(1);
                    items.add(item);
                } while(cursor.moveToNext());
            }
        } catch(Exception e){
            items = null;
        }
        return items;
    }

    public List<Item> search(String keyword){
        List<Item> items = null;
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + itemTable + " WHERE " + nameColumn + " LIKE ?", new String[] {"%" + keyword + "%"});
            if(cursor.moveToFirst()){
                items = new ArrayList<Item>();
                do{
                    Item item = new Item();
                    item.setName(cursor.getString(0));
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
