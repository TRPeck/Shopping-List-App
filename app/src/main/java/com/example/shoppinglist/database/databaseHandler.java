package com.example.shoppinglistmaster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shoppinglistmaster.model.makeListModel;

import java.util.ArrayList;
import java.util.List;


public class databaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "shoppingListDatabase";
    private static final String LIST_TABLE = "list";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String STATUS = "status";
    private static final String SHOPPINGLIST_TABLE = "CREATE TABLE " + LIST_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase data_base;

    public databaseHandler(Context context) {

        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SHOPPINGLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);
        onCreate(sqLiteDatabase);
    }
    public void openDatabase() {

        data_base = this.getWritableDatabase();
    }
    public void insertTask(makeListModel task){
        ContentValues cv = new ContentValues();
        cv.put(ITEM, task.getList());
        cv.put(STATUS, 0);
        data_base.insert(LIST_TABLE, null, cv);
    }
    public List<makeListModel> getAllTasks(){
        List<makeListModel> taskList = new ArrayList<>();
        Cursor cur = null;
        data_base.beginTransaction();
        try{
            cur = data_base.query(LIST_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        makeListModel task = new makeListModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setList(cur.getString(cur.getColumnIndex(ITEM)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            data_base.endTransaction();
            //assert cur != null;
            cur.close();
        }
        return taskList;
    }
    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        data_base.update(LIST_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }
    public void updateItem(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM, task);
        data_base.update(LIST_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }
    public void deleteItem(int id){
        data_base.delete(LIST_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }

}
