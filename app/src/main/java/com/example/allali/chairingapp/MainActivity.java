package com.example.allali.chairingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static ArrayList<Chair> chairs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // give the tableLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        chairs = Chair.getChairsFromFile("chairs.json", this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * ReservationFragment
     */
    public static class ReservationFragment extends Fragment {
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

        public ReservationFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ReservationFragment newInstance(int sectionNumber) {
            ReservationFragment fragment = new ReservationFragment();
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
            final ChairAdapter chairAdapter = new ChairAdapter(getActivity(), chairs, 1);
            gridView.setAdapter(chairAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    Chair chair = chairs.get(position);
                    chair.toggleSelected();
                    chairAdapter.notifyDataSetChanged();
                }
            });

            Button reservationButton = (Button) rootView.findViewById(R.id.reservation_button);
            reservationButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Log.i("reservation Button", "clicked");
                    Intent intent = new Intent(getContext(), ReservationDetailsActivity.class);
                    startActivity(intent);
                }
            });
            return rootView;
        }
    }

    public static  class ReservationHistoryFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public ReservationHistoryFragment() {
        }

        public static  ReservationHistoryFragment newInstance(int sectionNumber) {
            ReservationHistoryFragment fragment = new ReservationHistoryFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return  fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootVIew = inflater.inflate(R.layout.fragment_reservation_history, container, false);
            ListView mListView;
            mListView = (ListView)rootVIew.findViewById(R.id.reserved_chairs_list_view);

            final ChairAdapter chairAdapter = new ChairAdapter(getActivity(), chairs, 2);
            mListView.setAdapter(chairAdapter);
            return rootVIew;
        }

    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return ReservationFragment.newInstance(position + 1);
                case 1:
                    return ReservationHistoryFragment.newInstance(position+1);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "予約";
                case 1:
                    return "予約一覧";
            }
            return null;
        }
    }
}
