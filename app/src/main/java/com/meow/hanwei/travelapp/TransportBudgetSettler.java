package com.meow.hanwei.travelapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by bernard on 22/11/2016.
 */

public class TransportBudgetSettler {
    private HashMap<Integer,Integer> associatedDistanceMap;
    private double budgetForTrip;
    private Map<Integer, String> sortedDistanceMap;



    public double calculateTaxiCost(int distance){
        double TAXIFLAGDOWN=3.60;
        double TAXIRATEPRE10PER400M=0.22;
        double TAXIRATEPOST10PER350M=0.22;

        double sumOfFees = TAXIFLAGDOWN;
        int currentDistance = 0;
        while (currentDistance<distance){
            if (currentDistance < 10000){
                currentDistance += 400;
                sumOfFees += TAXIRATEPRE10PER400M;
            }
            else{
                currentDistance += 350;
                sumOfFees += TAXIRATEPOST10PER350M;
            }
        }
        return Double.valueOf(String.format("%.2f", sumOfFees));
    }

    public double calculatePublicTransportCost(int distance){
        double PUBLICTRANSPORTFlAGDOWN=1.4;
        double PUBLICTRANSPORTRATEPER3KM=0.2;
        double PUBLICTRANSPORTCAP=2.5;

        double sumOfFees = PUBLICTRANSPORTFlAGDOWN;
        int currentDistance = 0;
        while (sumOfFees<PUBLICTRANSPORTCAP && currentDistance<distance){
            currentDistance+=3000;
            sumOfFees+=PUBLICTRANSPORTRATEPER3KM;
        }
        if (sumOfFees>PUBLICTRANSPORTCAP) sumOfFees = PUBLICTRANSPORTCAP;

        return Double.valueOf(String.format("%.2f", sumOfFees));
    }


    TransportBudgetSettler(double newBudget, HashMap<Integer, Integer> newAssociatedDistanceMap){
        this.budgetForTrip = newBudget;
        this.associatedDistanceMap = newAssociatedDistanceMap;

        this.sortedDistanceMap = sortByValues(newAssociatedDistanceMap);

//        Iterator it = this.associatedDistanceMap.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            System.out.println("asDMap "+(int)pair.getKey() + " : "+ (int) pair.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
//        }
    }


    //to sort by distance
    public HashMap sortByValues(HashMap<Integer,Integer> mapToSort) {
        List list = new LinkedList(mapToSort.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
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

    public int getTotalDistance (){
        int lengthOfTravel = 0;
        for (int value : this.associatedDistanceMap.values()) {
            lengthOfTravel+= value;
        }
        return lengthOfTravel;
    }

    public ArrayList<String> optimiseTransportMode() {
        //gradually reduce or upgrade to fit budget

        ArrayList<String> transportMeans = new ArrayList<String>();

        //initialise all to public transport
        double costForWholeTrip = 0;
        for (int distance : this.associatedDistanceMap.values()) {
            costForWholeTrip+= calculatePublicTransportCost(distance);
        }

        System.out.println("Cost for whole trip via public transport is " +costForWholeTrip);
        for (int i = 0; i < associatedDistanceMap.size(); i++){
            transportMeans.add("Public transport");
        }


        Set set2 = sortedDistanceMap.entrySet();
        Iterator iterator2 = set2.iterator();

        while (costForWholeTrip>budgetForTrip && iterator2.hasNext()){     //if over budget, downgrade shortest one
            Map.Entry me2 = (Map.Entry)iterator2.next();
            int index = (int) me2.getKey();
            if (!transportMeans.get(index).equals("Walk")) {
                //downgrade and recheck cost
                if (transportMeans.get(index).equals("Public transport")) {  //downgrade from bus to walk
                    costForWholeTrip -= calculatePublicTransportCost((int) me2.getValue());
                    transportMeans.set(index, "Walk"); //update transportation means
                }
            }
        }

        //reset iterator
        iterator2 = set2.iterator();
        NavigableMap<Integer, Integer> reverseMap = new TreeMap<Integer, Integer>();
        while (iterator2.hasNext()){
            //fill up the reversible map with the sorted map
            Map.Entry me2 = (Map.Entry)iterator2.next();
            reverseMap.put((int)me2.getKey(), (int)me2.getValue());
        }

        set2 = reverseMap.descendingMap().entrySet();
        iterator2 = set2.iterator();

        while (costForWholeTrip+5 < budgetForTrip && iterator2.hasNext()){  //if less than budget, (try and) upgrade the biggest possible ones to a cab
            Map.Entry me2 = (Map.Entry)iterator2.next();
            int index = (int) me2.getKey();
            if (transportMeans.get(index).equals("Public transport")){
                double costIfCab = calculateTaxiCost((int)me2.getValue());
                double costIfPublic = calculatePublicTransportCost((int)me2.getValue());
                if (costForWholeTrip-costIfPublic+costIfCab <=budgetForTrip){   //got budget to change
                    costForWholeTrip = costForWholeTrip-costIfPublic+costIfCab;
                    transportMeans.set(index, "Cab"); //update transportation means
                }
            }
        }

        transportMeans.add(0,"Total cost is "+Double.valueOf(String.format("%.2f", costForWholeTrip)));
        return transportMeans;
    }
}
