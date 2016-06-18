package at.fhooe.mc.android.travel;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import at.fhooe.mc.android.R;


public class TravelEdit extends Fragment {



    private FloatingActionButton fab;
    TravelListItem item;

    public TravelEdit() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = MyTravelsMenu_ListFragment.toDelete;
        fab = MyTravelsMenu.fab;

    }


    @Override
    public void onStart() {
        super.onStart();

        fab.setImageResource(R.drawable.ic_delete_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        if (item != null){

            EditText et = (EditText)getView().findViewById(R.id.travel_edit_to);
            et.setText(item.getTo());

            et = (EditText) getView().findViewById(R.id.travel_edit_from);
            et.setText(item.getFrom());

            et = (EditText) getView().findViewById(R.id.travel_edit_date);
            et.setText(item.dateToString());

            et = (EditText) getView().findViewById(R.id.travel_edit_time);
            et.setText(item.getTime());

            TextView tv = (TextView)getView().findViewById(R.id.travel_edit_personNumber);
            tv.setText(String.valueOf(item.getPersons()));

            tv = (TextView)getView().findViewById(R.id.travel_edit_trainNumber);
            tv.setText(String.valueOf(item.getTrainNumber()));

        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_edit, container, false);
    }



}
