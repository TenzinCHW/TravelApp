package com.meow.hanwei.travelapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by HanWei on 20/11/2016.
 */
public class ASyncHttps extends AsyncTask<String, Void, String> {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private String KEY = "AIzaSyDKl5Kpec3loPgTSW9hpU6R5in2ojl3RB8";
    private String JSONStr;
    public ArrayList<Integer> dist;

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        getDistFromJSON(JSONStr);
    }

    @Override
    protected String doInBackground(String... strings) {
        String stringUrl = strings[0];
        String inputLine;
        String result;
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

    public void getJSONString(String fromPlace, ArrayList<String> toPlaces) {
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
            JSONStr = result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void getDistFromJSON(String time2parse) throws ArrayStoreException {
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
        dist = result;
    }
}
