package at.fhooe.mc.android.travel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Date;
import java.util.Iterator;

import at.fhooe.mc.android.R;


public class MyTravelsMenu_ListFragment extends Fragment {

    public TravelListArrayAdapter listAdapter;
    static TravelListItem toDelete;
    public FloatingActionButton fabBut;


    public MyTravelsMenu_ListFragment(){

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

        fabBut = MyTravelsMenu.fab;

        listAdapter = new TravelListArrayAdapter(getActivity());


        MyTravelsMenu.fragment = "List";

        fabBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Railchat_New_Travel.class);
                startActivity(i);
            }
        });
        fabBut.setImageResource(R.drawable.ic_add_black_24dp);

        ListView listView = (ListView)getView().findViewById(R.id.travel_list);
        listView.setAdapter(listAdapter);
        databaseTravel();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toDelete = (TravelListItem)parent.getItemAtPosition(position);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.myTravels_frameLayout, new TravelOverview());
                fragmentTransaction.commit();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.travel_delete_title)
                        .setMessage(R.string.travel_delete_message)
                        .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.drawable.ic_delete_black_24dp)
                        .show();

                return true;
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int oldTop;
            private int oldVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (scrollUp(view, firstVisibleItem, visibleItemCount, totalItemCount)){
                    fabBut.animate().translationY(0).setInterpolator(new LinearInterpolator()).start();
                }
                else {
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fabBut.getLayoutParams();
                    int fab_bottomMargin = layoutParams.bottomMargin;
                    MyTravelsMenu.fab.animate().translationY(fabBut.getHeight() + fab_bottomMargin).setInterpolator(new LinearInterpolator()).start();
                }

            }


            public boolean scrollUp(AbsListView absView, int firstVisibleItem, int visibleItemCount, int totalItemCount){

                View view = absView.getChildAt(0);
                int top = (view == null) ? 0 : view.getTop();
                boolean up = false;

                if (firstVisibleItem == oldVisibleItem) {
                    if (top > oldTop) {
                        //UP
                        up = true;
                    } else if (top < oldTop) {
                        //DOWN
                        up = false;
                    }
                    else {
                        up = true;
                    }
                } else {
                    if (firstVisibleItem < oldVisibleItem) {
                        //UP
                        up = true;
                    } else {
                        //DOWN
                        up = false;
                    }
                }

                oldTop = top;
                oldVisibleItem = firstVisibleItem;
                return up;
            }

        });

    }

    public void databaseTravel(){

        MyTravelsMenu.myRef.addChildEventListener(new ChildEventListener() {
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
                    String date = travel.child("Date").getValue().toString();
                    String time = travel.child("Time").getValue().toString();
                    int persons = 0;
                    int trainNumber = Integer.parseInt(train);

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
                    String date = travel.child("Date").getValue().toString();
                    String time = travel.child("Time").getValue().toString();
                    int persons = 0;
                    int trainNumber = Integer.parseInt(train);

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
                    String date = travel.child("Date").getValue().toString();
                    String time = travel.child("Time").getValue().toString();
                    int persons = 0;
                    int trainNumber = Integer.parseInt(train);

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