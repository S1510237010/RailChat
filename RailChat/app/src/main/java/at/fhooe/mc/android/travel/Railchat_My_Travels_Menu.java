package at.fhooe.mc.android.travel;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.Iterator;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.InitializeDatabase;
import at.fhooe.mc.android.main_menu.Railchat_Main_Menu;

public class Railchat_My_Travels_Menu extends Fragment {
    private static final String TAG = "Railchat:myTravels";
    public DatabaseReference myRef;
    public String userID = "8c03c4dd-17b1-42aa-af94-e7846cb5049c";
    public TravelListArrayAdapter listAdapter;


    public Railchat_My_Travels_Menu() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myRef = Railchat_Main_Menu.database.getDatabase().getReference("User");
        myRef.child(userID);
        listAdapter = new TravelListArrayAdapter(getActivity());


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_railchat_my_travels_menue, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_and_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();
        ListView listView = (ListView)getView().findViewById(R.id.travel_list);
        listView.setAdapter(listAdapter);
        databaseTravel();

        if (listAdapter.isEmpty()){
            Toast.makeText(getActivity(), "You  dont have any Travels saved.. :(", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()){
            case R.id.add_button:{
                i = new Intent(getActivity(), Railchat_New_Travel.class);
            }break;
            default: {
                Log.e(TAG, "unexpected option item");} break;
        }

        if (i != null){
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void databaseTravel(){

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                listAdapter.clear();

                Iterable<DataSnapshot> travels = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = travels.iterator();

                while (iterator.hasNext()) {

                    DataSnapshot travel = iterator.next();
                    String to = travel.child("To").getValue().toString();
                    String from = travel.child("From").getValue().toString();
                    String train = travel.child("Train").getValue().toString();
                    String dateString = travel.child("Date").getValue().toString();
                    String time = "00:00";
                    int persons = 0;
                    int trainNumber = Integer.parseInt(train);

                    String[] dateData = dateString.split("-");
                    int day = Integer.parseInt(dateData[0]);
                    int month = Integer.parseInt(dateData[1]);
                    int year = Integer.parseInt(dateData[2]);
                    Date date = new Date(year, month, day);

                    TravelListItem item = new TravelListItem(to, from, trainNumber, persons, date, time);
                    listAdapter.add(item);

                }

                updateAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                listAdapter.clear();

                Iterable<DataSnapshot> travels = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = travels.iterator();

                while (iterator.hasNext()) {

                    DataSnapshot travel = iterator.next();
                    String to = travel.child("To").getValue().toString();
                    String from = travel.child("From").getValue().toString();
                    String train = travel.child("Train").getValue().toString();
                    String dateString = travel.child("Date").getValue().toString();
                    String time = "00:00";
                    int persons = 0;
                    int trainNumber = Integer.parseInt(train);

                    String[] dateData = dateString.split("-");
                    int day = Integer.parseInt(dateData[0]);
                    int month = Integer.parseInt(dateData[1]);
                    int year = Integer.parseInt(dateData[2]);
                    Date date = new Date(year, month, day);

                    TravelListItem item = new TravelListItem(to, from, trainNumber, persons, date, time);
                    listAdapter.add(item);

                }

                updateAdapter();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                listAdapter.clear();

                Iterable<DataSnapshot> travels = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = travels.iterator();

                while (iterator.hasNext()) {

                    DataSnapshot travel = iterator.next();
                    String to = travel.child("To").getValue().toString();
                    String from = travel.child("From").getValue().toString();
                    String train = travel.child("Train").getValue().toString();
                    String dateString = travel.child("Date").getValue().toString();
                    String time = "00:00";
                    int persons = 0;
                    int trainNumber = Integer.parseInt(train);

                    String[] dateData = dateString.split("-");
                    int day = Integer.parseInt(dateData[0]);
                    int month = Integer.parseInt(dateData[1]);
                    int year = Integer.parseInt(dateData[2]);
                    Date date = new Date(year, month, day);

                    TravelListItem item = new TravelListItem(to, from, trainNumber, persons, date, time);
                    listAdapter.add(item);

                }

                updateAdapter();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void updateAdapter(){

        if (listAdapter.isEmpty()){
            Toast.makeText(getActivity(), "You  dont have any Travels saved.. :(", Toast.LENGTH_SHORT).show();
        }

        listAdapter.notifyDataSetChanged();
    }


}
