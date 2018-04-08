package com.example.allali.chairingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class SelectedChairsListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Chair> selectedChairs;
    private Chair selectedChair;

    private LayoutInflater inflater;

    public SelectedChairsListAdapter(Activity context, ArrayList<Chair> chairs) {
        this.context = context;
        this.selectedChairs = chairs;

        this.inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return selectedChairs.size();
    }

    @Override
    public Object getItem(int position) {
        return selectedChairs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        selectedChair = selectedChairs.get(position);
        view = inflater.inflate(R.layout.selected_chair, parent, false);
        final TextView chairName = (TextView) view.findViewById(R.id.selectedChairName);
        final TextView chairPlace = (TextView) view.findViewById(R.id.selectedChairPlace);
        final TextView chairReservationDateTime = (TextView) view.findViewById(R.id.selectedChairDateTime);

        final ImageView selectDateTime = (ImageView) view.findViewById(R.id.selectDateTime);
        chairName.setText(selectedChair.getName());
        chairPlace.setText(selectedChair.getReservationPlace());
        chairReservationDateTime.setText(selectedChair.getReservationDate() + " " + selectedChair.getReservationTime());

        selectDateTime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "date time clicked", Toast.LENGTH_LONG).show();
                final RelativeLayout rootLayout = (RelativeLayout) inflater.inflate(R.layout.dialog_date_time, null);
                final DatePicker datePicker = (DatePicker) rootLayout.findViewById(R.id.datePicker);
                final TimePicker timePicker = (TimePicker) rootLayout.findViewById(R.id.timePicker);

                new AlertDialog.Builder(context)
                        .setTitle("reservation time")
                        .setView(rootLayout)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                chairReservationDateTime.setText(datePicker.getYear() + "/" + datePicker.getMonth() + "/" + datePicker.getDayOfMonth() +  " " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                            }
                        }).create().show();
            }
        });
        return view;
    }
}
