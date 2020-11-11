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

public class EditListActivity extends AppCompatActivity {
    // activity where the user will be able to edit their lists and add new items
    // opens the search

    // todo: create adapter for recyclerview. code selecting items, removing items, database connectivity

    // database for item list and adapter for displaying it
    private SQLiteDatabase mDatabase;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // these are for displaying a list of items gathered from a database
        ListDBHelper dbHelper = new ListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.listOfItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
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

    // method to query database and fill recyclerview
    private Cursor getAllItems(){
        return mDatabase.query(
                ListContract.ItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ListContract.ItemEntry.COLUMN_AISLE + " ASC"
        );
    }

}