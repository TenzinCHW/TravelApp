package com.meow.hanwei.travelapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));




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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.clear();
                adapter.addAll(WordCorrector.wordFix(s, data.getAllPlaces()));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("TYPE ");

                return false;
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

class WordCorrector {
    public static ArrayList<String> wordFix(String word, ArrayList<String> allPlaces) {
        int minDistance;
        int tempDistance;
        HashMap<Integer, String> likelyWordsStack = new HashMap<Integer, String>();
        ArrayList<String> mostLikelyWords = new ArrayList<String>();



        System.out.println("LENGTH OF ARRAYLIST IS " +allPlaces.size());
        for (String wordtest : allPlaces) {
            minDistance = 999;
            if (wordtest.length()>word.length()){
                for (int i = 0; i < (wordtest.length() +1 - word.length()); i++) {
                    String temp = wordtest.substring(i, i + word.length() - 1);
                    tempDistance = levenshteinDistance(word, temp);
                    if (tempDistance<minDistance) minDistance= tempDistance;

                    if (tempDistance < minDistance) {        //compare if the next word matches the input better than the previous word
                        minDistance = tempDistance;
                        }
                }
                likelyWordsStack.put(minDistance,wordtest);
            }
            else{
                tempDistance = levenshteinDistance(word, wordtest);
                if (tempDistance < minDistance) {        //compare if the next word matches the input better than the previous word
                    minDistance = tempDistance;
                }
                likelyWordsStack.put(minDistance,wordtest);
            }
        }

        //sort likelywordstack, get top 5
        Map<Integer, String> sortedDistanceMap = sortByValues(likelyWordsStack);
        Set set2 = sortedDistanceMap.entrySet();
        Iterator iterator2 = set2.iterator();
        int i = 0;
        while (i < 5 && iterator2.hasNext()){     //if over budget, downgrade shortest one
            Map.Entry me2 = (Map.Entry)iterator2.next();
            mostLikelyWords.add((String) me2.getValue());
        }

        return mostLikelyWords;
    }

    public static HashMap sortByValues(HashMap<Integer,String> mapToSort) {
        List list = new LinkedList(mapToSort.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getKey())
                        .compareTo(((Map.Entry) (o2)).getKey());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }



    //Levenshtein calculates the word distance between the user input, and the actual word. The smaller (up to 0), the closer the word. Lifted shamelessly off the net
    public static int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        for (int i = 0; i < len0; i++) cost[i] = i;

        for (int j = 1; j < len1; j++) {
            newcost[0] = j;
            for (int i = 1; i < len0; i++) {
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);

            }
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }
        return cost[len0 - 1];
    }
}