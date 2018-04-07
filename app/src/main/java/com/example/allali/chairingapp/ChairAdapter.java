package com.example.allali.chairingapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by allali on 4/4/2018.
 */

public class ChairAdapter extends BaseAdapter {
    private final Activity mContext;
    private LayoutInflater mInflater;
    private final ArrayList<Chair> chairs;

    private Chair chair;

    private BluetoothAdapter mBlAdapter = null;
    public ChairAdapter(Activity context, ArrayList<Chair> chairs) {
        this.mContext = context;
        // add the instance variables that will be associated  with the adapter
        this.mInflater = (LayoutInflater)  mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chairs = chairs;
        mBlAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBlAdapter == null) {
            Toast.makeText(context, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getCount() {
         return chairs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return chairs.get(position);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // find the proper chair to display in the cell by using the position
        chair = chairs.get(position);
        View rowView = convertView;
        Log.i("getView top", "position:" + position);
            // GridView optimizes memory usage by recycling the cells. This means that if convertView is null
            // you instantiate a new cell view by using a LayoutInflater and inflating your linearlayout_chair layout
            // you create references for each individual view you created in your XML layout file
            rowView = mInflater.inflate(R.layout.chair_layout, parent, false);
            final ImageView chairImage = (ImageView) rowView.findViewById(R.id.chair_image);
            final TextView chairName = (TextView) rowView.findViewById(R.id.chair_name);
            // You set Chair's image, and name
            chairImage.setImageResource(chair.getIsSelected() ? R.drawable.chair_red : chair.getImageRessource());
            chairName.setText(chair.getName());
        return rowView;
    }

}
