package at.fhooe.mc.android.travel.travelmenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.getbase.floatingactionbutton.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.travel.travellist.DeleteTravel;
import at.fhooe.mc.android.travel.travellist.TravelListArrayAdapter;
import at.fhooe.mc.android.travel.travellist.TravelListItem;
import at.fhooe.mc.android.travel.newtravel.NewTravel;

/**
 * This class is the first Fragment in the Activity "MyTravels".
 * In this fragment all travels are shown as a list.
 * The Floating Button has the options:
 * - add
 * - delete all
 *
 * with a long click on a List item, the list item can be deleted.
 * with a short click on a list item, more details are shown of the travel:
 * TravelOverview will take the place of MyTravelsMenuListFragment in this case.
 */
public class MyTravelsMenuListFragment extends Fragment {

    private TravelListArrayAdapter listAdapter;
    static TravelListItem toDelete;
    private FloatingActionsMenu fabMenu;
    private FloatingActionButton fabBut, fabDelete;
    private boolean element = false;


    public MyTravelsMenuListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_travels_menu__list, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        fabBut = MyTravelsMenu.fab_add;
        fabDelete = MyTravelsMenu.fab_delete;
        fabMenu = MyTravelsMenu.fab_menu;

        listAdapter = new TravelListArrayAdapter(getActivity());


        MyTravelsMenu.fragment = "List";

        /**
         * Floating - Button : add Travel
         */
        fabBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), NewTravel.class);
                startActivity(i);
            }
        });
        fabBut.setImageResource(R.drawable.ic_add);

        /**
         * Floating Button : delete all with an AlertDialog to make sure it is wanted
         */
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.travel_delete_title)
                        .setMessage(R.string.travel_delete_all_message)
                        .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = listAdapter.getCount() - 1; i >= 0; i--) {
                                    new DeleteTravel(listAdapter.getItem(i));
                                }
                            }
                        })
                        .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.drawable.ic_delete)
                        .show();
            }
        });


        /**
         * setting the listAdapter and calling getTravel();
         * and the onItemClickListener
         */
        ListView listView = (ListView) getView().findViewById(R.id.travel_list);
        listView.setAdapter(listAdapter);
        getTravel();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toDelete = (TravelListItem) parent.getItemAtPosition(position);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.myTravels_frameLayout, new TravelOverview());
                fragmentTransaction.commit();
            }
        });

        /**
         * Delete one Travel Item
         */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.travel_delete_title)
                        .setMessage(R.string.travel_delete_message)
                        .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeleteTravel((TravelListItem) parent.getItemAtPosition(position));
                            }
                        })
                        .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.drawable.ic_delete)
                        .show();

                return true;
            }
        });
    }


    /**
     * This method adds all Travels from the database to the listadapter and clears it before the first
     * element is added.
     * When a travel is removed, the certain travel item is also removed from the listadapter.
     * Then the listadapter is updated.
     */
    public void getTravel(){


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
