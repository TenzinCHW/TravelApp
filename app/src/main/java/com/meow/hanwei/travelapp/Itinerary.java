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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;


public class Itinerary extends AppCompatActivity {
    static database data = new database();                              //database where we get all our location names for singapore
    final static ArrayList<String> places = new ArrayList<String>();        //ArrayList attached to adapter, to display the results of the shortest path wrt budget to the user
    static ArrayAdapter<String> itemsAdapter;                               //adapter for above-mentioned ArrayList


    static ArrayList<String> orderOfLocation = new ArrayList<String>();     //stores the order of which lookup occurs for the url
    static List<List<Integer>> distanceMap = new ArrayList<List<Integer>>();    //stores the downloaded distances for all the nodes, in a table format indicated by orderOfLocation
    static ArrayList<Integer> orderOfVisit;                                 //stores the generated shortest path for travelling salesman
    static ArrayList<Integer> distanceArray = new ArrayList<Integer>();     //stores the distance for the finalised path for every vertex, wrt orderOfVisit
    //static HashMap<Integer,Integer> distanceReduce = new HashMap<Integer, Integer>();   //stores the distance for the finalised path for every vertex, wrt orderOfVisit

    public void updateAdapter(){
        places.clear();
        if (distanceMap.size()==distanceMap.get(0).size()) {        //generate shortest path once all the input is in
            orderOfVisit = TSPNearestNeighbour.getShortestPath(distanceMap);
            for (int index : orderOfVisit){
                places.add(orderOfLocation.get(index));
            }
            places.add(orderOfLocation.get(0)); //home is the last place

            for (int i = 0; i < orderOfVisit.size()-1; i++){        //generate distance for the shortest path route, by looking it up from distanceMap's chart
                distanceArray.add(distanceMap.get(orderOfVisit.get(i)).get(orderOfVisit.get(i+1)));
            }
            distanceArray.add(distanceMap.get(orderOfVisit.get(orderOfVisit.size()-1)).get(0)); //include the distance from last node back to home

            //Temporary display of calculated distance
            String distance = "";
            for (int i = 0; i < distanceArray.size()-1 ; i++){
                distance+= "To "+ orderOfLocation.get(orderOfVisit.get(i)) +": " + Integer.toString(distanceArray.get(i)) +"\n";
            }
            distance+= "To "+ orderOfLocation.get(0) +": " + Integer.toString(distanceArray.get(distanceArray.size()-1 )) +"\n";
            places.add(distance);
        }
        else places.add("Loading places");
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_itinerary);

        Bundle extras = getIntent().getExtras();  // use this to get the locations the user input,
        // TODO: set rawUserLocations to the Arraylist in bundle

        //initialise Adapter, and link it to the adapter listening out there for asynctasks
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

        //Temporary sample used, format of <STARTING HOTEL>, ARRAYLIST OF ITENARY
       generateDistanceMap("Sentosa Singapore", data.getParks());
    }


    public void generateDistanceMap(String originHotel, ArrayList<String> placesToVisit){
        //order of distanceMap table is originHotel, placesToVisit0, placesToVisit1, ...

        orderOfLocation.add(0,originHotel);
        for (int i = 0; i < placesToVisit.size(); i++){
            orderOfLocation.add(placesToVisit.get(i));
        }

        for (int i = 0; i < placesToVisit.size()+1 ;i++ ){
            getJSONString(orderOfLocation.get(i),orderOfLocation); //response contains the distance from origin to places
        }
    }

    //private String KEY = "AIzaSyDKl5Kpec3loPgTSW9hpU6R5in2ojl3RB8";   //tenzin's key
    //private String KEY = "AIzaSyAbhohlm26WVT_H8HJMkFMghB5QGm4Mzc0"; //bernard's key
    //private String KEY = "AIzaSyBw4_44qvcaVt8Mdk6UCuAteotYSS2lQ10"; //Jiahong's key
    private String KEY = "AIzaSyAreMaarsk5IVDsEo0nteGjVxMPGxWdfvY"; //Alan's key

    public void getJSONString(String fromPlace, ArrayList<String> toPlaces) {
        String request = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + fromPlace.replace(" ", "+") + "&destinations=";
        int count = 0;

        while (count < toPlaces.size()) {
            request += toPlaces.get(count).replace(" ", "+");
            if (count < toPlaces.size() - 1) {
                request += "|";
            }
            count++;
        }
        request += "&mode=walking&key=" + KEY;

        new obtainDistanceAsync().execute(request);
    }

    public class obtainDistanceAsync extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;


        private ArrayList<Integer> getDistFromJSON(String time2parse) throws ArrayStoreException {
            System.out.println(time2parse);         //output string from the api call. For debugging purposes
            ArrayList<Integer> result = new ArrayList<>();
            if (time2parse.equals("FAILED")) {
                throw new ArrayStoreException("NOTHING FOUND GG");
            } else {
                try {
                    JSONObject originalData = new JSONObject(time2parse);
                    JSONArray rowsData = originalData.getJSONArray("rows");
                    JSONArray elementData = rowsData.getJSONObject(0).getJSONArray("elements");

                    for (int i = 0; i < elementData.length(); i++) {
                        int temp =elementData.getJSONObject(i).getJSONObject("distance").getInt("value");
                        result.add(temp);
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
            distanceMap.add(getDistFromJSON(result));
            updateAdapter();

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

    }

}



//Get shortest distance iteratively
class TSPNearestNeighbour {
    private int numberOfNodes;
    private Stack<Integer> stack;
    public TSPNearestNeighbour() {
        stack = new Stack<Integer>();
    }

    public ArrayList<Integer> tsp(int adjacencyMatrix[][]) {
        ArrayList<Integer> visitOrder= new ArrayList<Integer>();
        numberOfNodes = adjacencyMatrix[1].length;
        int[] visited = new int[numberOfNodes];
        visited[0] = 1;
        stack.push(0);
        int element, dst = 0, i;
        int min = Integer.MAX_VALUE;
        boolean minFlag = false;
        System.out.print(0 + "\t");
        visitOrder.add(0);

        while (!stack.isEmpty()) {
            element = stack.peek();
            i = 1;
            min = Integer.MAX_VALUE;
            while (i < numberOfNodes) {
                if (adjacencyMatrix[element][i] > 1 && visited[i] == 0) {
                    if (min > adjacencyMatrix[element][i]) {
                        min = adjacencyMatrix[element][i];
                        dst = i;
                        minFlag = true;
                    }
                }
                i++;
            }

            if (minFlag) {
                visited[dst] = 1;
                stack.push(dst);
                System.out.print(dst + "\t");
                visitOrder.add(dst);
                minFlag = false;
                continue;
            }

            stack.pop();
        }
        return visitOrder;
    }

    public static ArrayList<Integer> getShortestPath(List<List<Integer>> distanceMap) {
        int number_of_nodes;
        try {
            number_of_nodes = distanceMap.get(0).size();        //no of places to visit, including home

            //Generate the adjacency matrix (Consider replacing with the original distanceMap instead)
            int adjacency_matrix[][] = new int[number_of_nodes][number_of_nodes];
            for (int i = 0; i < number_of_nodes; i++) {
                for (int j = 0; j < number_of_nodes; j++) {
                    adjacency_matrix[i][j]=distanceMap.get(i).get(j);
                }
            }

            for (int i = 0; i < number_of_nodes; i++) {
                for (int j = 0; j < number_of_nodes; j++)
                {
                    if (adjacency_matrix[i][j] == 1 && adjacency_matrix[j][i] == 0) {
                        adjacency_matrix[j][i] = 1;
                    }
                }
            }

            System.out.println("Visit in the order of:");
            TSPNearestNeighbour tspNearestNeighbour = new TSPNearestNeighbour();
            return tspNearestNeighbour.tsp(adjacency_matrix);
        } catch (InputMismatchException inputMismatch) {
            System.out.println("Wrong Input format");
        }
        return null;
    }
}


