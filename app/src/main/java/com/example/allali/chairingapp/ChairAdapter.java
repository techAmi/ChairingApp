package com.example.allali.chairingapp;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by allali on 4/4/2018.
 */

public class ChairAdapter extends BaseAdapter {
    private final Activity mContext;
    private LayoutInflater mInflater;
    private final ArrayList<Chair> chairs;
    private final int listType;
    private static final int GRID = 1;
    private static final int LIST = 2;


    public ChairAdapter(Activity context, ArrayList<Chair> chairs, int listType) {
        this.mContext = context;
        // add the instance variables that will be associated  with the adapter
        this.mInflater = (LayoutInflater)  mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chairs = chairs;
        this.listType = listType;
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
        final Chair chair = chairs.get(position);
        View rowView = convertView;
        if (listType == GRID) {
            // GridView optimizes memory usage by recycling the cells. This means that if convertView is null
            // you instantiate a new cell view by using a LayoutInflater and inflating your linearlayout_chair layout
            // you create references for each individual view you created in your XML layout file
            rowView = mInflater.inflate(R.layout.chair_layout, parent, false);
            final ImageView chairImage = (ImageView) rowView.findViewById(R.id.chair_image);
            final TextView chairName = (TextView) rowView.findViewById(R.id.chair_name);
            // You set Chair's image, and name
            chairImage.setImageResource(chair.getIsSelected() ? R.drawable.chair_red : chair.getImageRessource());
            chairName.setText(chair.getName());
        }

        if (listType == LIST) {
            rowView = mInflater.inflate(R.layout.reserved_chair_layout, parent, false);
            final ImageView chairImage = (ImageView) rowView.findViewById(R.id.chair_image);
            final TextView chairName = (TextView) rowView.findViewById(R.id.reserved_chair_name);
            final TextView chairReservationPlace = (TextView) rowView.findViewById(R.id.reservationPlace);
            final TextView chairReservationDate = (TextView) rowView.findViewById(R.id.reservationDate);
            final TextView chairReservationTime = (TextView) rowView.findViewById(R.id.reservationTime);
            chairImage.setImageResource(R.drawable.chair_white);
            chairName.setText(chair.getName());
            chairReservationPlace.setText(chair.getReservationPlace());
            chairReservationDate.setText(chair.getReservationDate());
            chairReservationTime.setText(chair.getReservationTime());

            // Lock and imageViews click events

            final ImageView lockChair = (ImageView) rowView.findViewById(R.id.lockChair);
            final ImageView unlockChair = (ImageView) rowView.findViewById(R.id.unlockChair);

            lockChair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Locked", "was clicked");
//                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    mContext.startActivityForResult(enableIntent, 2);
                    new IntentIntegrator(mContext).initiateScan();
                }
            });

            unlockChair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Unlocked", "was clicked");
                }
            });
        }


        return rowView;
    }


}
