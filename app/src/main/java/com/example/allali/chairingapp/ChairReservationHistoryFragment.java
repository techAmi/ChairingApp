package com.example.allali.chairingapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ChairReservationHistoryFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<Chair> chairs;

    public ChairReservationHistoryFragment() {

    }

    public static ChairReservationHistoryFragment newInstance(int sectionNumber) {
        ChairReservationHistoryFragment fragment = new ChairReservationHistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chairs = Chair.getChairsFromFile("chairs.json", getActivity());
        View rootVIew = inflater.inflate(R.layout.fragment_reservation_history, container, false);
        ListView mListView;
        mListView = (ListView)rootVIew.findViewById(R.id.reserved_chairs_list_view);

        final ChairListAdapter chairAdapter = MainActivity.chairListAdapter;
        mListView.setAdapter(chairAdapter);
        return rootVIew;
    }
}
