package com.meow.hanwei.travelapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class Itinerary extends AppCompatActivity {
    static database data = new database();
    final static ArrayList<String> places = new ArrayList<String>();
    static ArrayAdapter<String> itemsAdapter;

    public void updateAdapter(String result){
        places.add(result);
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_itinerary);

        Bundle extras = getIntent().getExtras();  // use this to get the locations the user input
        // TODO: set rawUserLocations to the Arraylist in bundle

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, places);

        ListView listView = (ListView) findViewById(R.id.itinerary);
        listView.setAdapter(itemsAdapter);

        itemsAdapter.notifyDataSetChanged();

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

       generateDistanceMap("Sentosa Singapore", data.getEntertainment());
        //getJSONString("Fullerton Hotel", data.getParks());
    }


    static List<List<Integer>> distanceMap = new ArrayList<List<Integer>>();


    public void generateDistanceMap(String originHotel, ArrayList<String> placesToVisit){
        //order of distanceMap table is originHotel, placesToVisit0, placesToVisit1, ...
        ArrayList<String> orderOfLocation = new ArrayList<String>();
        orderOfLocation.add(0,originHotel);
        for (int i = 0; i < placesToVisit.size(); i++){
            orderOfLocation.add(placesToVisit.get(i));
        }

        for (int i = 0; i < placesToVisit.size()+1 ;i++ ){
            distanceMap.add(new ArrayList<Integer>());
            getJSONString(orderOfLocation.get(i),orderOfLocation); //response contains the distance from origin to places
        }
    }

    private String KEY = "AIzaSyDKl5Kpec3loPgTSW9hpU6R5in2ojl3RB8";

    public void getJSONString(String fromPlace, ArrayList<String> toPlaces) {
        String request = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + fromPlace.replace(" ", "+") + "&destinations=";
        int count = 0;

        while (count < toPlaces.size()) {
            request += toPlaces.get(count).replace(" ", "+");
            if (count < toPlaces.size() - 1) {
                request += "|";
            }
            count ++;
        }
        request += "&mode=walking&key=" + KEY;

        new getDistanceMapping().execute(request);
    }

    public class getDistanceMapping extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;


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



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //distanceMap.add(getDistFromJSON(result));
            updateAdapter(result);
            //itemsAdapter.this.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            //System.out.println(stringUrl);
            String inputLine;
            String result;

            //for (something : noOfPlaces){ do try/except below}

            try{
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpsURLConnection connection =(HttpsURLConnection) myUrl.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();

            }catch (IOException e){
                e.printStackTrace();
                result = null;
            }

            return result;
        }

    }

}