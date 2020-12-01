package com.example.shoppinglistmaster.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglistmaster.MainActivity;
import com.example.shoppinglistmaster.R;
import com.example.shoppinglistmaster.addNewItem;
import com.example.shoppinglistmaster.database.databaseHandler;
import com.example.shoppinglistmaster.model.makeListModel;

import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {
    private List<makeListModel> makeListModels;
    private MainActivity activity;
    private databaseHandler db;


    public listAdapter(databaseHandler db, MainActivity activity) {
        this.activity = activity;
        this.db = db;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db.openDatabase();
        makeListModel list=makeListModels.get(position);
        holder.list.setText(list.getList());
        holder.list.setChecked(toBoolean(list.getStatus()));
        holder.list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(list.getId(), 1);
                } else {
                    db.updateStatus(list.getId(), 0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return makeListModels.size();
    }

    private boolean toBoolean(int n)
    {
        return n != 0;
    }

public void setItem(List<makeListModel> makeListModels){
        this.makeListModels = makeListModels;
        notifyDataSetChanged();
}
    public Context getContext() {
        return activity;
    }

    public void editItem(int position) {
        makeListModel item = makeListModels.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("list", item.getList());
        addNewItem fragment = new addNewItem();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), addNewItem.TAG);
    }
    public void deleteItem(int position) {
        makeListModel item = makeListModels.get(position);
        db.deleteItem(item.getId());
        makeListModels.remove(position);
        notifyItemRemoved(position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox list;

        ViewHolder(View view){
            super(view);
            list=view.findViewById(R.id.todoCheckBox);
        }
    }
}
