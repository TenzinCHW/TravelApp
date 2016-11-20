package com.meow.hanwei.travelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Itinerary extends AppCompatActivity {
    private String KEY = "AIzaSyDKl5Kpec3loPgTSW9hpU6R5in2ojl3RB8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        Bundle extras = getIntent().getExtras();  // use this to get the locations the user input
        // TODO: set rawUserLocations to the Arraylist in bundle
        database data = new database();


        final ArrayList<String> places = data.getMuseums();
        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, places);
        ListView listView = (ListView) findViewById(R.id.itinerary);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                places.remove(i);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        // TODO: make https request ASYNCHRONOUSLY
        // TODO: convert to JSON
        // TODO: read the distance data
    }

    private String getJSONString(String fromPlace, ArrayList<String> toPlaces) {
        String request = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + fromPlace.replace(" ", "+") + "&destinations=";
        int count = 0;
        while (count < toPlaces.size()) {
            request += toPlaces.get(count).replace(" ", "+");
            if (count < toPlaces.size() - 1) {
                request += "|";
            }
        }
        request += "&mode=walking&key=" + KEY;
        ASyncHttps getStuff = new ASyncHttps();
        String result;
        try {
            result = getStuff.execute(request).get();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "FAILED";
    }

    private ArrayList<Integer> getDistFromJSON(String time2parse) throws ArrayStoreException {
        ArrayList<Integer> result = new ArrayList<>();
        if (time2parse.equals("FAILED")) {
            throw new ArrayStoreException("NOTHING FOUND GG");
        } else {
            try {
                JSONObject originalData = new JSONObject(time2parse);
                JSONArray rowsData = (JSONArray) originalData.get("rows");
                JSONArray elementData;
                JSONObject distData;
                Integer dist;

                for (int i = 0; i < rowsData.length(); i++) {
                    elementData = (JSONArray) rowsData.get(i);
                    distData = (JSONObject) elementData.get(1);
                    dist = distData.getInt("value");
                    result.add(dist);
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }
        }
        return result;
    }

}