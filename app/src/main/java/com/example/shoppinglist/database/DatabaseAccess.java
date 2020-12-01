package com.example.shoppinglistmaster.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context){
        this.sqLiteOpenHelper = new ItemDBHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        if(sqLiteDatabase != null){
            this.sqLiteDatabase.close();
        }
    }
}
