package com.meow.hanwei.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashSet;
import java.util.Set;

public class PlaceInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        TextView placeName = (TextView) findViewById(R.id.placeName);
        Intent intent = getIntent();
        final String place = intent.getStringExtra(Intent.EXTRA_TEXT);
        placeName.setText(place);

        database data = new database();
//        HashMap<String,ArrayList<String>> referMap = new HashMap<String,ArrayList<String>>();
//        referMap.put("Parks",data.getParks());
//        referMap.put("Religious",data.getReligious());
//        referMap.put("Museums",data.getMuseums());
//        referMap.put("Food",data.getFood());
//        referMap.put("Entertainment",data.getEntertainment());

        String category = "Parks";
        int position = data.getParks().indexOf(place);
        if(position == -1){
            category = "Religious";
            position = data.getReligious().indexOf(place);
        }
        if(position == -1){
            category = "Museums";
            position = data.getMuseums().indexOf(place);
        }
        if(position == -1){
            category = "Food";
            position = data.getFood().indexOf(place);
        }
        if(position == -1){
            category = "Entertainment";
            position = data.getEntertainment().indexOf(place);
        }

        String output = "";
        if(position != -1) {
            switch (category) {
                case "Parks":
                    output = data.getParkInfo().get(position);
                    break;
                case "Religious":
                    output = data.getReligiousInfo().get(position);
                    break;
                case "Museums":
                    output = data.getMuseumInfo().get(position);
                    break;
                case "Food":
                    output = data.getFoodInfo().get(position);
                    break;
                case "Entertainment":
                    output = data.getEntertainmentInfo().get(position);
                    break;
                default:
                    output = "No such entry found";
            }
        } else {
            output = "Unable to find in index";
        }

        TextView placeInfo = (TextView) findViewById(R.id.placeInfo);
        placeInfo.setText(output);


        final SharedPreferences sharedPref = getSharedPreferences("Data",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        final Set<String> empty = new HashSet<String>();
        final Set<String> itinerary = sharedPref.getStringSet("Itinerary",empty);


        ToggleButton storeOrRemove = (ToggleButton) findViewById(R.id.storeOrRemove);
        storeOrRemove.setChecked(itinerary.contains(place));


        storeOrRemove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    itinerary.add(place);
                    editor.putStringSet("Itinerary",itinerary);
                    editor.commit();

                    Toast.makeText(getBaseContext(),itinerary.toString(),Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PlaceInfo.this, Search.class);
                    startActivity(intent);
                } else {
                    itinerary.remove(place);
                    editor.putStringSet("Itinerary",itinerary);
                    editor.commit();

                    Toast.makeText(getBaseContext(),itinerary.toString(),Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PlaceInfo.this, Search.class);
                    startActivity(intent);

                }
            }
        });

    }
}
