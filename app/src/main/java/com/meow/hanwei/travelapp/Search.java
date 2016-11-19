package com.meow.hanwei.travelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

public class Search extends AppCompatActivity {

    MultiSpinner parks;
    MultiSpinner religious;
    MultiSpinner museum;
    MultiSpinner entertainment;
    MultiSpinner food;
    ArrayList<String> selectedParks = new ArrayList<>();
    ArrayList<String> selectedReligious = new ArrayList<>();
    ArrayList<String> selectedMuseums = new ArrayList<>();
    ArrayList<String> selectedEntertainment = new ArrayList<>();
    ArrayList<String> selectedFood = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final database data = new database();

        // Multi spinner
//        parks = (MultiSpinner) findViewById(R.id.parks);
//        MultiSpinner.MultiSpinnerListener parkListener = new MultiSpinner.MultiSpinnerListener() {
//            @Override
//            public void onItemsSelected(boolean[] selected) {
//                selectedParks.clear();
//                System.out.println(Arrays.toString(selected));
//                System.out.println("park");
//                for (int i = 0; i < selected.length; i++) {
//                    if (selected[i]) {
//                        selectedParks.add(data.getParks().get(i));
//                    }
//                }
//                System.out.println(selectedParks.toString());
//            }
//        };
//        parks.setItems(data.getParks(), "Parks", parkListener);
//
//        religious = (MultiSpinner) findViewById(R.id.religious);
//        final MultiSpinner.MultiSpinnerListener religiousListener = new MultiSpinner.MultiSpinnerListener() {
//            @Override
//            public void onItemsSelected(boolean[] selected) {
//                System.out.println(Arrays.toString(selected));
//                System.out.println("reli");
//                selectedReligious.clear();
//                for (int i = 0; i < selected.length; i++) {
//                    if (selected[i]) {
//                        selectedReligious.add(data.getReligious().get(i));
//                    }
//                }
//                System.out.println(selectedReligious.toString());
//            }
//        };
//        religious.setItems(data.getReligious(), "Religious", religiousListener);
//
//        museum = (MultiSpinner) findViewById(R.id.museums);
//        MultiSpinner.MultiSpinnerListener museumListener = new MultiSpinner.MultiSpinnerListener() {
//            @Override
//            public void onItemsSelected(boolean[] selected) {
//                System.out.println(selected.length);
//                selectedMuseums.clear();
//                for (int i = 0; i < selected.length; i++) {
//                    if (selected[i]) {
//                        selectedMuseums.add(data.getMuseums().get(i));
//                    }
//                }
//            }
//        };
//        museum.setItems(data.getMuseums(), "Museums", museumListener);
//
//        entertainment = (MultiSpinner) findViewById(R.id.entertainment);
//        MultiSpinner.MultiSpinnerListener entertainmentListener = new MultiSpinner.MultiSpinnerListener() {
//            @Override
//            public void onItemsSelected(boolean[] selected) {
//                System.out.println(selected.length);
//                for (int i = 0; i < selected.length; i++) {
//                    if (selected[i]) {
//                        selectedLocations.add(data.getEntertainment().get(i));
//                    } else{
//                        selectedLocations.remove(data.getEntertainment().get(i));
//                    }
//                }
//            }
//        };
//        entertainment.setItems(data.getEntertainment(), "Entertainment", entertainmentListener);
//
//        food = (MultiSpinner) findViewById(R.id.food);
//        MultiSpinner.MultiSpinnerListener foodListener = new MultiSpinner.MultiSpinnerListener() {
//            @Override
//            public void onItemsSelected(boolean[] selected) {
//                System.out.println(selected.length);
//                for (int i = 0; i < selected.length; i++) {
//                    if (selected[i]) {
//                        selectedLocations.add(data.getFood().get(i));
//                    }else {
//                        selectedLocations.remove(data.getFood().get(i));
//                    }
//                }
//            }
//        };
//        food.setItems(data.getFood(), "Food", foodListener);

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
