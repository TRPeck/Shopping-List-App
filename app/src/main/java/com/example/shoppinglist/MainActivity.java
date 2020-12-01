package com.example.shoppinglistmaster;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglistmaster.adapter.listAdapter;
import com.example.shoppinglistmaster.database.databaseHandler;
import com.example.shoppinglistmaster.model.makeListModel;
import com.example.shoppinglistmaster.model.map;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements com.example.shoppinglistmaster.DialogCloseListener {
    private RecyclerView listRecyclerView;
    private listAdapter listsAdapter;
    private FloatingActionButton fab;
    private List<makeListModel> taskList;
    private databaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.map:
                        startActivity(new Intent(getApplicationContext(), map.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



        db = new databaseHandler(this);
        db.openDatabase();
        taskList = new ArrayList<>();

        listRecyclerView = findViewById(R.id.listsRecyclerView);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listsAdapter = new listAdapter(db,this);
        listRecyclerView.setAdapter(listsAdapter);

        fab = findViewById(R.id.fab);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new com.example.shoppinglistmaster.itemTouchHelper(listsAdapter));
        itemTouchHelper.attachToRecyclerView(listRecyclerView);
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        listsAdapter.setItem(taskList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem.newInstance().show(getSupportFragmentManager(), addNewItem.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        listsAdapter.setItem(taskList);
        listsAdapter.notifyDataSetChanged();
    }
}