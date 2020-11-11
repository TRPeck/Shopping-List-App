package com.example.shoppinglist;

import android.provider.BaseColumns;

public class ListContract {
    // this class defines string constants, for helping other classes
    private ListContract(){}

    public static final class ItemEntry implements BaseColumns {
        // todo enter strings for the actual name of table and columns for the database
        public static final String TABLE_NAME = "groceryList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AISLE = "aisle";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
