package at.fhooe.mc.android.database;

import android.os.AsyncTask;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import at.fhooe.mc.android.main_menu.MainMenu;

/**
 * Created by Anna on 21.06.2016.
 */
public class GetTrains {

    String keyFrom, keyTo, date;
    private final ArrayList<String> railjets = new ArrayList<String>();
    private final ArrayList<String> persons = new ArrayList<String>();
    public DatabaseReference myRef_RJ;



    public GetTrains(String keyFrom, String keyTo, String date){
        myRef_RJ        = MainMenu.database.getDatabase().getReference("RailJets");
        this.keyFrom    = keyFrom;
        this.keyTo      = keyTo;
        this.date       = date;

        new DownloadTrains().execute();
    }

    public ArrayList getTrains(){
        return railjets;
    }

    public String getPersons(String trainNumber){

        Iterator<String> trainsIT = railjets.iterator();
        Iterator<String> personsIT = persons.iterator();

        while (trainsIT.hasNext()){

            String actTrain = trainsIT.next();

            if (actTrain.equals(trainNumber)){
                return personsIT.next();
            }
            personsIT.next();
        }

        return null;
    }

    public void editPersons(String train, String date, int edit){
        int persons = Integer.valueOf(getPersons(train));
        edit += persons;
        myRef_RJ.child(train).child("Traveler").child(date).child("Persons").setValue(edit);
    }


    private class DownloadTrains extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            System.out.println("doInBackground");

            final int finalEven = Integer.parseInt(keyFrom) - Integer.parseInt(keyTo);
            final String finalIndex_from = keyFrom;
            final String finalIndex_to = keyTo;

            myRef_RJ.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot != null) {

                        boolean hasStation = false;
                        int trainNumber = Integer.parseInt(dataSnapshot.getKey());

                        if (finalEven > 0 && trainNumber % 2 == 0) {

                            Iterable<DataSnapshot> stations = dataSnapshot.child("StationNumber").getChildren();
                            Iterator<DataSnapshot> iterator_station = stations.iterator();

                            boolean to = false, from = false;

                            while (iterator_station.hasNext()) {

                                DataSnapshot trainstation = iterator_station.next();

                                if (trainstation != null) {
                                    String stationNumber = trainstation.getValue().toString();

                                    if (stationNumber.equals(finalIndex_from)) {
                                        from = true;
                                    }

                                    if (stationNumber.equals(finalIndex_to)) {
                                        to = true;
                                    }
                                }

                            }

                            if (from && to) {
                                hasStation = true;
                            }

                        } else if (finalEven < 0 && trainNumber % 2 == 1) {

                            Iterable<DataSnapshot> stations = dataSnapshot.child("StationNumber").getChildren();
                            Iterator<DataSnapshot> iterator_station = stations.iterator();

                            boolean to = false, from = false;

                            while (iterator_station.hasNext()) {
                                DataSnapshot childTrain = iterator_station.next();

                                if (childTrain != null) {
                                    String stationNumber = childTrain.getValue().toString();

                                    if (stationNumber.equals(finalIndex_from)) {
                                        from = true;
                                    }

                                    if (stationNumber.equals(finalIndex_to)) {
                                        to = true;
                                    }
                                }
                            }

                            if (from && to) {
                                hasStation = true;
                            }
                        } else if (finalEven == 0) {

                            System.out.println(trainNumber);

                            Iterable<DataSnapshot> stations = dataSnapshot.child("StationNumber").getChildren();
                            Iterator<DataSnapshot> iterator_station = stations.iterator();

                            boolean to = false, from = false;

                            if (finalIndex_from.equals("-1")) {
                                from = true;
                            }
                            if (finalIndex_to.equals("-1")) {
                                to = true;
                            }

                            while (iterator_station.hasNext()) {
                                DataSnapshot childTrain = iterator_station.next();

                                if (childTrain != null) {
                                    String stationNumber = childTrain.getValue().toString();

                                    if (stationNumber.equals(finalIndex_from)) {
                                        from = true;
                                    }

                                    if (stationNumber.equals(finalIndex_to)) {
                                        to = true;
                                    }
                                }
                            }

                            if (from && to) {
                                hasStation = true;
                            }
                        }

                        if (hasStation) {
                            railjets.add(dataSnapshot.getKey());
//                            persons.add(dataSnapshot.child("Traveler").child(date).child("Persons").getValue().toString());
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot != null) {

                        boolean hasStation = false;
                        int trainNumber = Integer.parseInt(dataSnapshot.getKey());

                        if (finalEven > 0 && trainNumber % 2 == 0) {

                            Iterable<DataSnapshot> stations = dataSnapshot.child("StationNumber").getChildren();
                            Iterator<DataSnapshot> iterator_station = stations.iterator();

                            boolean to = false, from = false;

                            while (iterator_station.hasNext()) {

                                DataSnapshot trainstation = iterator_station.next();

                                if (trainstation != null) {
                                    String stationNumber = trainstation.getValue().toString();

                                    if (stationNumber.equals(finalIndex_from)) {
                                        from = true;
                                    }

                                    if (stationNumber.equals(finalIndex_to)) {
                                        to = true;
                                    }
                                }

                            }

                            if (from && to) {
                                hasStation = true;
                            }

                        } else if (finalEven < 0 && trainNumber % 2 == 1) {

                            Iterable<DataSnapshot> stations = dataSnapshot.child("StationNumber").getChildren();
                            Iterator<DataSnapshot> iterator_station = stations.iterator();

                            boolean to = false, from = false;

                            while (iterator_station.hasNext()) {
                                DataSnapshot childTrain = iterator_station.next();

                                if (childTrain != null) {
                                    String stationNumber = childTrain.getValue().toString();

                                    if (stationNumber.equals(finalIndex_from)) {
                                        from = true;
                                    }

                                    if (stationNumber.equals(finalIndex_to)) {
                                        to = true;
                                    }
                                }
                            }

                            if (from && to) {
                                hasStation = true;
                            }
                        } else if (finalEven == 0) {

                            System.out.println(trainNumber);

                            Iterable<DataSnapshot> stations = dataSnapshot.child("StationNumber").getChildren();
                            Iterator<DataSnapshot> iterator_station = stations.iterator();

                            boolean to = false, from = false;

                            if (finalIndex_from.equals("-1")) {
                                from = true;
                            }
                            if (finalIndex_to.equals("-1")) {
                                to = true;
                            }

                            while (iterator_station.hasNext()) {
                                DataSnapshot childTrain = iterator_station.next();

                                if (childTrain != null) {
                                    String stationNumber = childTrain.getValue().toString();

                                    if (stationNumber.equals(finalIndex_from)) {
                                        from = true;
                                    }

                                    if (stationNumber.equals(finalIndex_to)) {
                                        to = true;
                                    }
                                }
                            }

                            if (from && to) {
                                hasStation = true;
                            }
                        }

                        if (hasStation) {
                            railjets.add(dataSnapshot.getKey());
//                            persons.add(dataSnapshot.child("Traveler").child(date).child("Persons").getValue().toString());
                        }
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {

                        boolean hasStation = false;
                        int trainNumber = Integer.parseInt(dataSnapshot.getKey());

                        if (finalEven > 0 && trainNumber % 2 == 0) {

                            Iterable<DataSnapshot> stations = dataSnapshot.child("StationNumber").getChildren();
                            Iterator<DataSnapshot> iterator_station = stations.iterator();

                            boolean to = false, from = false;

                            while (iterator_station.hasNext()) {

                                DataSnapshot trainstation = iterator_station.next();

                                if (trainstation != null) {
                                    String stationNumber = trainstation.getValue().toString();

                                    if (stationNumber.equals(finalIndex_from)) {
                                        from = true;
                                    }

                                    if (stationNumber.equals(finalIndex_to)) {
                                        to = true;
                                    }
                                }

                            }

                            if (from && to) {
                                hasStation = true;
                            }

                        } else if (finalEven < 0 && trainNumber % 2 == 1) {

                            Iterable<DataSnapshot> stations = dataSnapshot.child("StationNumber").getChildren();
                            Iterator<DataSnapshot> iterator_station = stations.iterator();

                            boolean to = false, from = false;

                            while (iterator_station.hasNext()) {
                                DataSnapshot childTrain = iterator_station.next();

                                if (childTrain != null) {
                                    String stationNumber = childTrain.getValue().toString();

                                    if (stationNumber.equals(finalIndex_from)) {
                                        from = true;
                                    }

                                    if (stationNumber.equals(finalIndex_to)) {
                                        to = true;
                                    }
                                }
                            }

                            if (from && to) {
                                hasStation = true;
                            }
                        } else if (finalEven == 0) {

                            System.out.println(trainNumber);

                            Iterable<DataSnapshot> stations = dataSnapshot.child("StationNumber").getChildren();
                            Iterator<DataSnapshot> iterator_station = stations.iterator();

                            boolean to = false, from = false;

                            if (finalIndex_from.equals("-1")) {
                                from = true;
                            }
                            if (finalIndex_to.equals("-1")) {
                                to = true;
                            }

                            while (iterator_station.hasNext()) {
                                DataSnapshot childTrain = iterator_station.next();

                                if (childTrain != null) {
                                    String stationNumber = childTrain.getValue().toString();

                                    if (stationNumber.equals(finalIndex_from)) {
                                        from = true;
                                    }

                                    if (stationNumber.equals(finalIndex_to)) {
                                        to = true;
                                    }
                                }
                            }

                            if (from && to) {
                                hasStation = true;
                            }
                        }

                        if (hasStation) {
                            railjets.add(dataSnapshot.getKey());
//                            persons.add(dataSnapshot.child("Traveler").child(date).child("Persons").getValue().toString());
                        }
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


//            myRef_RJ.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Iterable<DataSnapshot> snapshot_Train = dataSnapshot.getChildren();
//                    Iterator<DataSnapshot> iterator = snapshot_Train.iterator();
//
//                    while (iterator.hasNext()) {
//                        DataSnapshot child = iterator.next();
//
//                        if (child != null) {
//
//                            boolean hasStation = false;
//                            int trainNumber = Integer.parseInt(child.getKey());
//
//                            if (finalEven > 0 && trainNumber % 2 == 0){
//
//                                Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
//                                Iterator<DataSnapshot> iterator_station = stations.iterator();
//
//                                boolean to = false, from = false;
//
//                                while(iterator_station.hasNext()){
//
//                                    DataSnapshot trainstation = iterator_station.next();
//
//                                    if (trainstation != null){
//                                        String stationNumber = trainstation.getValue().toString();
//
//                                        if (stationNumber.equals(finalIndex_from)){
//                                            from = true;
//                                        }
//
//                                        if (stationNumber.equals(finalIndex_to)){
//                                            to = true;
//                                        }
//                                    }
//
//                                }
//
//                                if (from && to){
//                                    hasStation = true;
//                                }
//
//                            }
//                            else if (finalEven < 0 && trainNumber % 2 == 1){
//
//                                Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
//                                Iterator<DataSnapshot> iterator_station = stations.iterator();
//
//                                boolean to = false, from = false;
//
//                                while(iterator_station.hasNext()){
//                                    DataSnapshot childTrain = iterator_station.next();
//
//                                    if (childTrain != null){
//                                        String stationNumber = childTrain.getValue().toString();
//
//                                        if (stationNumber.equals(finalIndex_from)){
//                                            from = true;
//                                        }
//
//                                        if (stationNumber.equals(finalIndex_to)){
//                                            to = true;
//                                        }
//                                    }
//                                }
//
//                                if (from && to){
//                                    hasStation = true;
//                                }
//                            }
//                            else if (finalEven == 0){
//
//                                System.out.println(trainNumber);
//
//                                Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
//                                Iterator<DataSnapshot> iterator_station = stations.iterator();
//
//                                boolean to = false, from = false;
//
//                                if (finalIndex_from.equals("-1")){
//                                    from = true;
//                                }
//                                if (finalIndex_to.equals("-1")){
//                                    to = true;
//                                }
//
//                                while(iterator_station.hasNext()){
//                                    DataSnapshot childTrain = iterator_station.next();
//
//                                    if (childTrain != null){
//                                        String stationNumber = childTrain.getValue().toString();
//
//                                        if (stationNumber.equals(finalIndex_from)){
//                                            from = true;
//                                        }
//
//                                        if (stationNumber.equals(finalIndex_to)){
//                                            to = true;
//                                        }
//                                    }
//                                }
//
//                                if (from && to){
//                                    hasStation = true;
//                                }
//                            }
//
//                            if (hasStation) {
//                                railjets.add(child.getKey());
//                                persons.add(child.child("Traveler").child(date).child("Persons").getValue().toString());
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//
//            });

            return null;
        }


    }



}
