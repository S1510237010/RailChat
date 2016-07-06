package at.fhooe.mc.android.database;

import android.app.Activity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import at.fhooe.mc.android.main_menu.MainMenu;
import at.fhooe.mc.android.travel.travellist.TravelListArrayAdapter;
import at.fhooe.mc.android.travel.travellist.TravelListItem;
import at.fhooe.mc.android.travel.travelmenu.MyTravelsMenu;

/**
 * This class gets all Travels of a user and has some other methods to control some fields of the
 * Travels:
 * - travelToday:
 *          controls if the user has today a travel
 */
public class GetTravels {

    private TravelListArrayAdapter listAdapter;
    boolean element;
    public static DatabaseReference myRef, trainRef;

    public GetTravels(Activity activity){
        listAdapter = new TravelListArrayAdapter(activity);
        element = false;
        myRef = MainMenu.database.getDatabase().getReference("Users");
        trainRef = MainMenu.database.getDatabase().getReference("Travels");
        getTravel();
    }

    public TravelListArrayAdapter getListAdapter(){
        return listAdapter;
    }

    public int travelToday(){

        StringBuffer date = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        date.append(calendar.get(Calendar.DAY_OF_MONTH));
        date.append("-");
        date.append((calendar.get(Calendar.MONTH) + 1));
        date.append("-");
        date.append(calendar.get(Calendar.YEAR));
//        Log.e("Dating Dates", date.toString() + " ListAdapterCount: " + listAdapter.getCount());

        for (int i = 0; i < listAdapter.getCount(); i++){
//            Log.e("Dating Dates", listAdapter.getItem(i).getDate());
            if (listAdapter.getItem(i).getDate().equals(date.toString())){
                return listAdapter.getItem(i).getTrainNumber();
            }
        }
        return 0;
    }

    /**
     * This method adds all Travels from the database to the listadapter and clears it before the first
     * element is added.
     * When a travel is removed, the certain travel item is also removed from the listadapter.
     * Then the listadapter is updated.
     */
    private void getTravel(){

        myRef.child(new GetUser().getUserID()).child("Travels").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String travelID = dataSnapshot.getValue().toString();

                System.out.println("OnChildAdded");

                if (!element){
                    listAdapter.clear();
                }


                trainRef.child(travelID).addListenerForSingleValueEvent(new ValueEventListener() {
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
