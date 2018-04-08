package com.example.allali.chairingapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectReservationDetailsActivity extends AppCompatActivity {
    ListView selectedChairsList;
    ArrayList<Chair> selectedChairs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_reservation_details);

        this.selectedChairsList = (ListView) findViewById(R.id.reserved_chairs_list_view);
        // TODO: code this the right way
        // this is just for demo purposes
        this.selectedChairs = Chair.getChairsFromFile("chairs.json", this);
        SelectedChairsListAdapter adapter = new SelectedChairsListAdapter(this, selectedChairs);
        selectedChairsList.setAdapter(adapter);
    }





}
