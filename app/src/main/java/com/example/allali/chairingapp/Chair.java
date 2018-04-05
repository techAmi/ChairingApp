package com.example.allali.chairingapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by allali on 4/4/2018.
 */

public class Chair {
    private int name;
    private boolean isReserved = false;
    private boolean isSelected = false;
    private boolean isConnected = false;
    private boolean isLocked = true;
    private final int imageRessource;
    private String reservationDate;
    private String reservationTime;
    private String place;

    public Chair(int name, int imageRessource) {
        this.name = name;
        this.imageRessource = imageRessource;
    }

    public String getName() {
        return "CH" + name;
    }

    public int getImageRessource() {
        if (!isReserved) {
            return imageRessource;
        } else return R.drawable.chair_red;

    }

    public boolean getIsReserved() {
        return isReserved;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public String getReservationPlace() {
        return this.place;
    }

    public String getReservationDate() {
        return this.reservationDate;
    }

    public String getReservationTime() {
        return  this.reservationTime;
    }

    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public void toggleSelected() {
        isSelected = !isSelected;
        Log.i("toggled", String.valueOf(isSelected));
    }
    public  void toggleConnected() {
        isConnected = !isConnected;
    }
    public void toggleLocked() {
        isLocked = !isLocked;
    }

    public static ArrayList<Chair> getChairsFromFile(String fileName, Context context) {
        final ArrayList<Chair> chairList = new ArrayList<>();

        try {
                String jsonString = loadJsonFromAsset(fileName, context);
                JSONObject json = new JSONObject(jsonString);
                JSONArray chairs = json.getJSONArray("chairs");

                for (int i = 0; i < chairs.length(); i++) {
                    Chair chair = new Chair(0, R.drawable.chair_black);
                    chair.name = Integer.parseInt(chairs.getJSONObject(i).getString("name"));
                    chair.place = chairs.getJSONObject(i).getString("place");
                    chair.reservationDate = chairs.getJSONObject(i).getString("date");
                    chair.reservationTime = chairs.getJSONObject(i).getString("time");

                    chairList.add(chair);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.print(chairList);
        return chairList;

    }


    private static String loadJsonFromAsset(String fileName, Context context) {
        String json = null;

        try{
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
