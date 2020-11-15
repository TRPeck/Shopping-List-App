package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ListsViewActivity extends AppCompatActivity {

    private static ListDBHelper listDBHelper;
    private long listCount = 1;
    private static long activeList;
    private static String activeListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listDBHelper = new ListDBHelper(getApplicationContext());
    }

    public static ListDBHelper getListDBHelper(){
        return listDBHelper;
    }

    public static long getActiveList(){
        return activeList;
    }

    public static String getActiveListName(){
        return activeListName;
    }

    public void newList(View view) {
        ShoppingList list = new ShoppingList("Untitled List " + listCount);
        activeListName = list.getListName();
        listCount++;
        long list_id = listDBHelper.createList(list);
        activeList = list_id;
        Intent intent = new Intent(this, EditListActivity.class);
        startActivity(intent);
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
                    .add(R.id.lvframe, mapFragment)
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }


}