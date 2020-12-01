package com.example.shoppinglistmaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shoppinglistmaster.R;
import com.example.shoppinglistmaster.model.Item;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    private ListView listView;


    public ItemAdapter(@NonNull Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Item item = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_in_list, parent, false);
        }
        TextView itemName = convertView.findViewById(R.id.list_item_name);
        TextView itemAisle = convertView.findViewById(R.id.list_item_aisle);
        itemName.setText(item.getName());
        itemAisle.setText("Aisle: " + item.getAisle());
        return convertView;
    }


}
