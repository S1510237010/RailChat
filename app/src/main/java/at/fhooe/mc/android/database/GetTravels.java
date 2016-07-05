package at.fhooe.mc.android.database;

import android.app.Activity;
import android.widget.ListAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import at.fhooe.mc.android.travel.travellist.TravelListArrayAdapter;
import at.fhooe.mc.android.travel.travellist.TravelListItem;
import at.fhooe.mc.android.travel.travelmenu.MyTravelsMenu;

/**
 * Created by Anna on 05.07.2016.
 */
public class GetTravels {

    private TravelListArrayAdapter listAdapter;
    boolean element;

    public GetTravels(Activity activity){
        listAdapter = new TravelListArrayAdapter(activity);
        element = false;
        getTravel();
    }

    public TravelListArrayAdapter getListAdapter(){
        return listAdapter;
    }

    public boolean travelToday(){

        StringBuffer date = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        date.append(calendar.get(Calendar.DAY_OF_YEAR));
        date.append("-");
        date.append((calendar.get(Calendar.MONTH) + 1));
        date.append("-");
        date.append(calendar.get(Calendar.YEAR));

        for (int i = 0; i < listAdapter.getCount(); i++){
            if (listAdapter.getItem(i).getDate().equals(date.toString())){
                return true;
            }
        }
        return false;
    }

    private void getTravel(){

        MyTravelsMenu.myRef.child(MyTravelsMenu.userID).child("Travels").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String travelID = dataSnapshot.getValue().toString();

                System.out.println("OnChildAdded");

                if (!element){
                    listAdapter.clear();
                }

                MyTravelsMenu.trainRef.child(travelID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (!dataSnapshot.exists()) {
                            return;
                        }

                        String to       = dataSnapshot.child("To").getValue().toString();
                        String from     = dataSnapshot.child("From").getValue().toString();
                        String train    = dataSnapshot.child("Train").getValue().toString();
                        String date     = dataSnapshot.child("Date").getValue().toString();
                        String time     = dataSnapshot.child("Time").getValue().toString();
//                            int persons     = Integer.parseInt(dataSnapshot.child("Persons").getValue().toString());
                        int trainNumber = Integer.parseInt(train);

                        TravelListItem item = new TravelListItem(travelID, to, from, trainNumber, 0, date, time);

                        boolean contains = false;

                        for (int i = 0; i < listAdapter.getCount(); i++){
                            if (item.equals(listAdapter.getItem(i))){
                                contains = true;
                            }
                        }

                        if (!contains){
                            listAdapter.add(item);
                        }

                        element = true;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                updateAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                final String travelID = dataSnapshot.getValue().toString();

                System.out.println("OnChildChanged");

                if (!element){
                    listAdapter.clear();

                }

                MyTravelsMenu.trainRef.child(travelID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (!dataSnapshot.exists()) {
                            return;
                        }

                        String to       = dataSnapshot.child("To").getValue().toString();
                        String from     = dataSnapshot.child("From").getValue().toString();
                        String train    = dataSnapshot.child("Train").getValue().toString();
                        String date     = dataSnapshot.child("Date").getValue().toString();
                        String time     = dataSnapshot.child("Time").getValue().toString();
//                            int persons     = Integer.parseInt(dataSnapshot.child("Persons").getValue().toString());
                        int trainNumber = Integer.parseInt(train);

                        TravelListItem item = new TravelListItem(travelID, to, from, trainNumber, 0, date, time);
                        boolean contains = false;

                        for (int i = 0; i < listAdapter.getCount(); i++){
                            if (item.equals(listAdapter.getItem(i))){
                                contains = true;
                            }
                        }

                        if (!contains){
                            listAdapter.add(item);
                        }
                        element = true;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                updateAdapter();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                final String travelID = dataSnapshot.getValue().toString();


                for (int i = 0; i < listAdapter.getCount(); i++){

                    TravelListItem item = listAdapter.getItem(i);
                    if(item.equals(new TravelListItem(travelID))){
                        listAdapter.remove(item);
                    }

                }

                listAdapter.remove(new TravelListItem(travelID));

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


    /**
     * This method updates the listadapter.
     */
    protected void updateAdapter(){
        element = false;
        listAdapter.notifyDataSetChanged();
    }




}
