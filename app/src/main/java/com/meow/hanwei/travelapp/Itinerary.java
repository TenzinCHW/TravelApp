package com.meow.hanwei.travelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Itinerary extends AppCompatActivity {
    // KEY: AIzaSyAx3Pj6JFDGef_UyhbiONNnAzCEtxlAuSQ

    ArrayList<String> rawUserLocations;
    //        Bukit Timah Nature Reserve
    String origin = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&mode=bicycling&key=AIzaSyAx3Pj6JFDGef_UyhbiONNnAzCEtxlAuSQ";
    ASyncHttps request = new ASyncHttps();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);


        Bundle extras = getIntent().getExtras();  // use this to get the locations the user input
        // TODO: set rawUserLocations to the Arraylist in bundle
        database data = new database();


        final ArrayList<String> foods = data.getMuseums();
        foods.add("meow");
        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);
        try{
            String addData = request.execute(origin).get();
            foods.add(addData);
            itemsAdapter.notifyDataSetChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        ListView listView = (ListView) findViewById(R.id.itinerary);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                foods.remove(i);
                itemsAdapter.notifyDataSetChanged();
            }
        });

        // TODO: make https request ASYNCHRONOUSLY
        // TODO: convert to JSON
        // TODO: read the distance data
    }

//    private ArrayList<String> sortLocations(){
//
//    }

}