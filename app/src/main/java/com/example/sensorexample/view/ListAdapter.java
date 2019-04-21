package com.example.sensorexample.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sensorexample.R;
import com.example.sensorexample.model.Items;

import java.util.ArrayList;

/*
 * Date 21st April 2019
 * App:- SensorDemo
 * Module:- Sensor list data
 * PageName:- ListAdapter.this
 * Description:- List Adapter class
 * Author:- Akhilesh Dubey
 * */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyListItem> {
    // define variable
    Context context;
    ArrayList<Items> items;

    // define constructor
    public ListAdapter(Context context, ArrayList<Items> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyListItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        MyListItem myListItem = new MyListItem(view);
        return myListItem;
    }

    @Override
    public void onBindViewHolder(@NonNull MyListItem myListItem, int i) {
        Items item = items.get(i);
        myListItem.txtAzimuth.setText(context.getString(R.string.azi_name) + " " + item.azimuth);
        myListItem.txtPitch.setText(context.getString(R.string.pitch_name) + " " + item.pitch);
        myListItem.txtRoll.setText(context.getString(R.string.roll_name) + " " + item.roll);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // view holder class
    class MyListItem extends RecyclerView.ViewHolder{
        TextView txtAzimuth, txtPitch, txtRoll;
        public MyListItem(@NonNull View itemView) {
            super(itemView);
            txtAzimuth = (TextView)itemView.findViewById(R.id.txtAzimuth);
            txtPitch = (TextView)itemView.findViewById(R.id.txtPitch);
            txtRoll = (TextView)itemView.findViewById(R.id.txtRoll);
        }
    }
}
