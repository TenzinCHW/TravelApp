package com.meow.hanwei.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final database data = new database();

        ToggleButton parksToggle = (ToggleButton) findViewById(R.id.parksToggle);
        ToggleButton religiousToggle = (ToggleButton) findViewById(R.id.religiousToggle);
        ToggleButton museumToggle = (ToggleButton) findViewById(R.id.museumToggle);
        ToggleButton foodToggle = (ToggleButton) findViewById(R.id.foodToggle);
        ToggleButton entertainmentToggle = (ToggleButton) findViewById(R.id.entertainmentToggle);
        ListView searchList = (ListView) findViewById(R.id.searchList);

        parksToggle.setChecked(true);
        religiousToggle.setChecked(true);
        museumToggle.setChecked(true);
        foodToggle.setChecked(true);
        entertainmentToggle.setChecked(true);

        ArrayList<String> list = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        searchList.setAdapter(adapter);
        adapter.addAll(data.getParks());
        adapter.addAll(data.getReligious());
        adapter.addAll(data.getMuseums());
        adapter.addAll(data.getFood());
        adapter.addAll(data.getEntertainment());
        adapter.setNotifyOnChange(true);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapter.getItem(i);
                Intent intent = new Intent(getBaseContext(),PlaceInfo.class)
                        .putExtra(Intent.EXTRA_TEXT,item);
                startActivity(intent);
            }
        });

        parksToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                ArrayList<String> parksList = data.getParks();
                if(isChecked){
                    adapter.addAll(parksList);
                } else {
                    for(String name:parksList){
                        adapter.remove(name);
                    }
                }
//                state.setText(String.valueOf(num));
            }
        });
        religiousToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                ArrayList<String> religiousList = data.getReligious();
                if(isChecked){
                    adapter.addAll(religiousList);
                } else {
                    for(String name:religiousList){
                        adapter.remove(name);
                    }
                }
            }
        });
        museumToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                ArrayList<String> museumList = data.getMuseums();
                if(isChecked){
                    adapter.addAll(museumList);
                } else {
                    for(String name:museumList){
                        adapter.remove(name);
                    }
                }
            }
        });
        foodToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                ArrayList<String> foodList = data.getFood();
                if(isChecked){
                    adapter.addAll(foodList);
                } else {
                    for(String name:foodList){
                        adapter.remove(name);
                    }
                }
            }
        });
        entertainmentToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                ArrayList<String> entertainmentList = data.getEntertainment();
                if(isChecked){
                    adapter.addAll(entertainmentList);
                } else {
                    for(String name:entertainmentList){
                        adapter.remove(name);
                    }
                }
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("Data",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> empty = new HashSet<String>();
        final Set<String> itinerary = sharedPref.getStringSet("Itinerary",empty);

        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.remove("Itinerary");
                editor.commit();
                Toast.makeText(getBaseContext(),"Cleared",Toast.LENGTH_SHORT).show();
            }
        });

        Button toItinerary = (Button) findViewById(R.id.toItinerary);
        toItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent meow = new Intent(Search.this, Itinerary.class);
                startActivity(meow);
            }
        });
    }
}
