package at.fhooe.mc.android.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import at.fhooe.mc.android.main_menu.Railchat_Main_Menu;

/**
 * Created by Anna on 21.06.2016.
 */
public class GetStations {

    private ArrayList<String> stationKeys = new ArrayList<String>();
    public DatabaseReference myRef_Station;
    private int keyFrom, keyTo;
    public ArrayList<String> stations = new ArrayList<String>();
    Activity activity;
    AutoCompleteTextView to, from;

    public GetStations(Activity activity) {
        this.activity = activity;
        new DownloadStations(activity).execute();
    }

    public String getKey(String s){

        Iterator<String> stationIt = stations.iterator();
        Iterator<String> keyIt = stationKeys.iterator();

        while(stationIt.hasNext()){

            String str = stationIt.next();
            String key = keyIt.next();

            if (str.equals(s)){
                return key;
            }

        }
        return null;
    }

    public boolean contains(String s){

        Iterator<String> stationIt = stations.iterator();

        while(stationIt.hasNext()){

            String str = stationIt.next();
            if (str.equals(s)){
                return true;
            }

        }
        return false;
    }


    private class DownloadStations extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        Activity activity;
        Context context;

        public DownloadStations(Activity activity){
            this.activity = activity;
            this.context = activity;
            dialog = new ProgressDialog(context);
        }



        @Override
        protected Void doInBackground(Void... voids) {

            final List list = Collections.synchronizedList(stations);

            myRef_Station = Railchat_Main_Menu.database.getDatabase().getReference("Stations");
            myRef_Station.keepSynced(true);
            myRef_Station.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    list.add(dataSnapshot.child("Name").getValue().toString());
                    stationKeys.add(dataSnapshot.getKey());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    list.add(dataSnapshot.child("Name").getValue().toString());
                    stationKeys.add(dataSnapshot.getKey());

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    list.add(dataSnapshot.child("Name").getValue().toString());
                    stationKeys.add(dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    list.add(dataSnapshot.child("Name").getValue().toString());
                    stationKeys.add(dataSnapshot.getKey());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }


}
