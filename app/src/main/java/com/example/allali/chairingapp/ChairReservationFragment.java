package com.example.allali.chairingapp;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChairReservationFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Chair[] chairsList = {
            new Chair(1, R.drawable.chair_black),
            new Chair(2, R.drawable.chair_black),
            new Chair(3, R.drawable.chair_black),
            new Chair(4, R.drawable.chair_black),
            new Chair(5, R.drawable.chair_black),
            new Chair(7, R.drawable.chair_black),
            new Chair(8, R.drawable.chair_black),
            new Chair(9, R.drawable.chair_black),
            new Chair(10, R.drawable.chair_black),
            new Chair(11, R.drawable.chair_black),
            new Chair(12, R.drawable.chair_black),
            new Chair(13, R.drawable.chair_black),
            new Chair(14, R.drawable.chair_black),
            new Chair(15, R.drawable.chair_black),
            new Chair(16, R.drawable.chair_black),
            new Chair(17, R.drawable.chair_black),
            new Chair(18, R.drawable.chair_black),
            new Chair(19, R.drawable.chair_black),
            new Chair(20, R.drawable.chair_black),
            new Chair(21, R.drawable.chair_black),
            new Chair(22, R.drawable.chair_black),
            new Chair(23, R.drawable.chair_black),
            new Chair(24, R.drawable.chair_black),
            new Chair(25, R.drawable.chair_black),
            new Chair(26, R.drawable.chair_black),
            new Chair(27, R.drawable.chair_black),
            new Chair(28, R.drawable.chair_black),
            new Chair(29, R.drawable.chair_black),
            new Chair(30, R.drawable.chair_black),
    };
    private ArrayList<Chair> chairs = new ArrayList<>(Arrays.asList(chairsList));

    public ChairReservationFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ChairReservationFragment newInstance(int sectionNumber) {
        ChairReservationFragment fragment = new ChairReservationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        final ChairGridAdapter chairGridAdapter = new ChairGridAdapter(getActivity(), chairs);
        gridView.setAdapter(chairGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Chair chair = chairs.get(position);
                chair.toggleSelected();
                chairGridAdapter.notifyDataSetChanged();
            }
        });

        Button reservationButton = (Button) rootView.findViewById(R.id.reservation_button);
        reservationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.i("reservation Button", "clicked");
                Intent intent = new Intent(getActivity(), SelectReservationDetailsActivity.class);
                startActivity(intent);

            }
        });
        return rootView;
    }
}
