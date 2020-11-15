package com.example.shoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditListActivity extends AppCompatActivity {
    // activity where the user will be able to edit their lists and add new items
    // opens the search

    // todo: create adapter for recyclerview. code selecting items, removing items, database connectivity

    // database for item list and adapter for displaying it
    private SQLiteDatabase mDatabase;
    private ListAdapter mAdapter;
    public static JSONArray myList;
    private RecyclerView recyclerView;
    private static ArrayList<Item> itemList;
    private ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.listOfItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // these are for displaying a list of items gathered from a database
        ListDBHelper listDBHelper = ListsViewActivity.getListDBHelper();
        String activeListName = ListsViewActivity.getActiveListName();

        ArrayList<Item> itemList = listDBHelper.getAllItemsByListName(activeListName);

        if(!itemList.isEmpty()){
            listAdapter = new ListAdapter(EditListActivity.this, itemList);
            recyclerView.setAdapter(listAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListDBHelper listDBHelper = ListsViewActivity.getListDBHelper();
        String activeListName = ListsViewActivity.getActiveListName();

        ArrayList<Item> itemList = listDBHelper.getAllItemsByListName(activeListName);

        if(!itemList.isEmpty()){
            listAdapter = new ListAdapter(EditListActivity.this, itemList);
            recyclerView.setAdapter(listAdapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_map){
            MapFragment mapFragment = new MapFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.elframe, mapFragment)
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItem(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


}