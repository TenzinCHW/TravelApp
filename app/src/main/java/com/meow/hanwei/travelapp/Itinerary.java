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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;

public class Itinerary extends AppCompatActivity {
    // KEY: AIzaSyAx3Pj6JFDGef_UyhbiONNnAzCEtxlAuSQ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
//        Bukit Timah Nature Reserve
        String origin = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&mode=bicycling&key=AIzaSyAx3Pj6JFDGef_UyhbiONNnAzCEtxlAuSQ";

        try {
            final ArrayList<String> foods = new ArrayList<>();
            foods.add("meow");
            URL url = new URL(origin);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            foods.add(getContent(connection));
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

    private String getContent(HttpsURLConnection con){
        if(con!=null){
            String content = "";
            try {
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));

                String input;

                while ((input = br.readLine()) != null){
                    content += input;
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }
        return "Nothing";
    }

}
