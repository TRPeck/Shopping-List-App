package com.example.shoppinglist;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    // activity that is required to implement the android search dialog

    private ListView listViewItem;
    private static ArrayList<Item> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        initView();
        loadData();

        databaseAccess.close();

        final ListDBHelper listDBHelper = ListsViewActivity.getListDBHelper();
        final long activeList = ListsViewActivity.getActiveList();

        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) listViewItem.getAdapter().getItem(position);
                listDBHelper.addItem(item, new long[]{activeList});
            }
        });
    }


    private void initView(){
        listViewItem = findViewById(R.id.searchList);
        registerForContextMenu(listViewItem);
    }

    private void loadData(){
        ItemDBHelper itemDBHelper = new ItemDBHelper(getApplicationContext());
        ArrayList<Item> items = itemDBHelper.findAll();
        if(items != null){
            listViewItem.setAdapter(new ItemAdapter(getApplicationContext(), items));
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchItem(newText);
                return false;
            }
        });

        return true;
    }

    private void searchItem(String keyword) {
        ItemDBHelper itemDBHelper = new ItemDBHelper(getApplicationContext());
        ArrayList<Item> items = itemDBHelper.search(keyword);
        if(items != null){
            listViewItem.setAdapter(new ItemAdapter(getApplicationContext(), items));
        }
    }

}