package com.example.allali.chairingapp;

import android.app.Activity;
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

public class ChairGridAdapter extends BaseAdapter {
    private final Activity mContext;
    private LayoutInflater mInflater;
    private final ArrayList<Chair> chairs;

    private Chair chair;

    public ChairGridAdapter(Activity context, ArrayList<Chair> chairs) {
        this.mContext = context;
        // add the instance variables that will be associated  with the adapter
        this.mInflater = (LayoutInflater)  mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chairs = chairs;
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
        rowView = mInflater.inflate(R.layout.chair_layout, parent, false);
        final ImageView chairImage = (ImageView) rowView.findViewById(R.id.chair_image);
        final TextView chairName = (TextView) rowView.findViewById(R.id.chair_name);

        Log.i("getView top", "position:" + position);

        chairImage.setImageResource(chair.getIsSelected() ? R.drawable.chair_red : chair.getImageRessource());
        chairName.setText(chair.getName());
        return rowView;
    }

}
