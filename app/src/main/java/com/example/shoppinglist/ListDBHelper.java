package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.shoppinglist.ListContract.*;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListDBHelper extends SQLiteOpenHelper {
    // this is a helper class to create and update database

    // todo: add database name
    private static final String DATABASE_NAME = "lists.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_LISTS = "lists";
    private static final String TABLE_ITEM_LISTS = "item_lists";

    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    private static final String KEY_NAME = "name";
    private static final String KEY_AISLE = "aisle";

    private static final String KEY_LIST_NAME = "list_name";

    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_LIST_ID = "list_id";

    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " +
            TABLE_ITEMS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME +
            " TEXT," + KEY_AISLE + " INTEGER," + KEY_CREATED_AT +
            " DATETIME" + ")";

    private static final String CREATE_TABLE_LISTS = "CREATE TABLE " +
            TABLE_LISTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LIST_NAME +
            " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

    private static final String CREATE_TABLE_ITEM_LISTS = "CREATE TABLE " +
            TABLE_ITEM_LISTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ITEM_ID +
            " INTEGER," + KEY_LIST_ID + " INTEGER," + KEY_CREATED_AT +
            " DATETIME" + ")";

    public ListDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_LISTS);
        db.execSQL(CREATE_TABLE_ITEM_LISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_LISTS);
        onCreate(db);
    }

    public long addItem(Item item, long[] list_ids){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, item.getName());
        contentValues.put(KEY_AISLE, item.getAisle());
        contentValues.put(KEY_CREATED_AT, getDateTime());

        long item_id = db.insert(TABLE_ITEMS, null, contentValues);

        for(long list_id:list_ids){
            createItemList(item_id, list_id);
        }

        return item_id;
    }

    public Item getItem(long item_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_ITEMS + " WHERE " +
                KEY_ID + " = " + item_id;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        Item item = new Item();
        item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        item.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        item.setAisle(cursor.getInt(cursor.getColumnIndex(KEY_AISLE)));
        item.setCreated_at(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));

        return item;
    }

    public List<Item> getAllItems(){
        List<Item> items = new ArrayList<Item>();
        String query = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                item.setAisle(cursor.getInt(cursor.getColumnIndex(KEY_AISLE)));
                item.setCreated_at(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));
                items.add(item);
            } while(cursor.moveToNext());
        }
        return items;
    }

    public ArrayList<Item> getAllItemsByListName(String list_name){
        ArrayList<Item> items = new ArrayList<Item>();
        String query = "SELECT * FROM " + TABLE_ITEMS + " td, " +
                TABLE_LISTS + " tg, " + TABLE_ITEM_LISTS + " tt WHERE tg." +
                KEY_LIST_NAME + " = '" + list_name + "'" + " AND tg." + KEY_ID +
                " = " + "tt." + KEY_LIST_ID + " AND td." + KEY_ID + " = " +
                "tt." + KEY_ITEM_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                item.setAisle(cursor.getInt(cursor.getColumnIndex(KEY_AISLE)));
                item.setCreated_at(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));
                items.add(item);
            } while(cursor.moveToNext());
        }
        return items;
    }

    public int updateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, item.getName());
        contentValues.put(KEY_AISLE, item.getAisle());

        return db.update(TABLE_ITEMS, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }

    public void deleteItem(long item_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[]{String.valueOf(item_id)});
    }

    public long createList(ShoppingList list){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LIST_NAME, list.getListName());
        contentValues.put(KEY_CREATED_AT, list.getCreated_at());

        long list_id = db.insert(TABLE_LISTS, null, contentValues);
        return list_id;
    }

    public List<ShoppingList> getAllLists(){
        List<ShoppingList> lists = new ArrayList<ShoppingList>();
        String query = "SELECT * FROM " + TABLE_LISTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                ShoppingList list = new ShoppingList();
                list.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                list.setListName(cursor.getString(cursor.getColumnIndex(KEY_LIST_NAME)));
                lists.add(list);
            } while(cursor.moveToNext());
        }
        return lists;
    }

    public int updateList(ShoppingList list){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LIST_NAME, list.getListName());

        return db.update(TABLE_ITEMS, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(list.getId())});
    }

    public void deleteList(ShoppingList list, boolean should_delete_all_items_lists){
        SQLiteDatabase db = this.getWritableDatabase();
        if(should_delete_all_items_lists){
            List<Item> allListItems = getAllItemsByListName(list.getListName());
            for(Item item:allListItems){
                deleteItem(item.getId());
            }
        }

        db.delete(TABLE_LISTS, KEY_ID + " = ?",
                new String[]{String.valueOf(list.getId())});
    }

    public long createItemList(long item_id, long list_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ITEM_ID, item_id);
        contentValues.put(KEY_LIST_ID, list_id);
        contentValues.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_ITEM_LISTS, null, contentValues);

        return id;
    }

    public int updateItemList(long id, long list_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LIST_ID, list_id);

        return db.update(TABLE_ITEMS, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    private String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
        );
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
