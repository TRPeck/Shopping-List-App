package com.example.shoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<Item> mItems;

    public ListAdapter(Context context, ArrayList<Item> items){
        mContext = context;
        mItems = items;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;
        public TextView aisleText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.list_item_name);
            aisleText = itemView.findViewById(R.id.list_item_aisle);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_in_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mItems.get(position);
        String itemName = item.getName();
        int itemAisle = item.getAisle();

        holder.nameText.setText(itemName);
        holder.aisleText.setText("Aisle:" + itemAisle);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
