package com.meow.hanwei.travelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Itinerary extends AppCompatActivity {

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
}