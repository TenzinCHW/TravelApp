package com.meow.hanwei.travelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;

public class Itinerary extends AppCompatActivity {
    // KEY: AIzaSyAx3Pj6JFDGef_UyhbiONNnAzCEtxlAuSQ


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
//        Bukit Timah Nature Reserve
        String origin = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&mode=bicycling&key=AIzaSyAx3Pj6JFDGef_UyhbiONNnAzCEtxlAuSQ";

        database data = new database();
        try {
            final ArrayList<String> foods = data.getMuseums();
            foods.add("meow");
//            URL url = new URL(origin);
//            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//            foods.add(getContent(connection));
            final ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);
            ListView listView = (ListView) findViewById(R.id.itinerary);
            listView.setAdapter(itemsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    foods.remove(i);
                    itemsAdapter.notifyDataSetChanged();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        // TODO: make https request
        // TODO: convert to JSON
        // TODO: read the distance data


    }

//    private ArrayList<String> sortLocations(){
//
//    }

}
