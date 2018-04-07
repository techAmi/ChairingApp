package com.example.allali.chairingapp;

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
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ChairListAdapter extends BaseAdapter {
    private final  Activity mContext;
    private LayoutInflater mInflater;
    private final ArrayList<Chair> chairs;
    private Chair chair;

    private ImageView chairImage;
    private TextView chairName;
    private TextView chairReservationPlace;
    private TextView chairReservationDate;
    private TextView chairReservationTime;

    private ImageView lockUnlockChair;
    private ImageView connectDisconnectChair;

    private BluetoothAdapter mBlAdapter = null;
    private UartService uartService = null;

    private int lastSelectedChair;

    public ChairListAdapter(Activity context, ArrayList<Chair> chairs) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chairs = chairs;
        this.mBlAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public int getCount() {
        return chairs.size();
    }

    @Override
    public Object getItem(int position) {
        return chairs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int selectedPos = position;
        // find the proper chair to display on the list using the postion
        chair = chairs.get(position);

        View rowView = mInflater.inflate(R.layout.reserved_chair_layout, parent, false);
        chairName = (TextView) rowView.findViewById(R.id.reserved_chair_name);
        chairReservationPlace = (TextView) rowView.findViewById(R.id.reservationPlace);
        chairReservationDate = (TextView) rowView.findViewById(R.id.reservationDate);
        chairReservationTime = (TextView) rowView.findViewById(R.id.reservationTime);

        lockUnlockChair = (ImageView) rowView.findViewById(R.id.lockUnlockChair);
        connectDisconnectChair = (ImageView) rowView.findViewById(R.id.connectDisconnectChair);
        lockUnlockChair.setImageResource((chair.getIsLocked() && !chair.getIsConnected()) ? R.drawable.locked_unconnected_chair : R.drawable.lock_chair);
        connectDisconnectChair.setImageResource(chairs.get(position).getIsConnected() ? R.drawable.connected : R.drawable.disconnected);
        Log.i("chair", ""+chairs.get(lastSelectedChair).getIsConnected());
        chairName.setText(chair.getName());
        chairReservationPlace.setText(chair.getReservationPlace());
        chairReservationDate.setText(chair.getReservationDate());
        chairReservationTime.setText(chair.getReservationTime());

        connectDisconnectChair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlAdapter.isEnabled()) {
                    Log.i("Bluetooth", "not available");
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    mContext.startActivityForResult(enableIntent, 2);
                } else {
                    ((MainActivity)mContext).setSelectedPos(selectedPos);
                    lastSelectedChair = selectedPos;
                    Log.i("Locked", "was clicked");
                    new IntentIntegrator(mContext).initiateScan();
                }

            }
        });

        lockUnlockChair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lockValue;
                Log.i("Unlocked", "was clicked");
                try {
                    if (chair.getIsConnected()) {
                        if (chair.getIsLocked()) {
                            lockValue = "O";
                        } else if(!chair.getIsLocked()) {
                            lockValue = "L";
                        } else {
                            lockValue = "stop";
                        }
                        if (uartService != null) {
                            uartService.writeRXCharacteristic(lockValue.getBytes("UTF-8"));
                        }
                        chair.toggleLocked();
                        lockUnlockChair.setImageResource(chair.getIsLocked() ? R.drawable.lock_chair : R.drawable.unlock_chair);
                    }
                } catch(UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }

        });



        return rowView;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String contents = intent.getStringExtra("SCAN_RESULT");
        Log.i("contents", contents);
        Toast.makeText(mContext, "Onctivityresult", Toast.LENGTH_LONG).show();
        if (contents != null || contents != null ) {
            Toast.makeText(mContext, "" + lastSelectedChair, Toast.LENGTH_LONG).show();
            Log.i("chair on act", ""+chairs.get(lastSelectedChair).getIsConnected());
            chairs.get(lastSelectedChair).toggleConnected();
            this.notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "No Scan Data Received", Toast.LENGTH_LONG).show();
        }
    }
}

