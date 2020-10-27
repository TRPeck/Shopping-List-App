package com.example.shoppinglist;

import android.provider.BaseColumns;

public class ItemContract {
    // this class defines string constants, for helping other classes
    private ItemContract(){}

    public static final class ItemEntry implements BaseColumns {
        // todo enter strings for the actual name of table and columns for the database
        public static final String TABLE_NAME = "placeholder";
        public static final String COLUMN_NAME = "";
        public static final String COLUMN_AISLE = "";
    }
}
