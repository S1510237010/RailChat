package at.fhooe.mc.android.travel;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.InitializeDatabase;
import at.fhooe.mc.android.main_menu.Railchat_Main_Menu;


public class Railchat_New_Travel_Stations extends Fragment implements View.OnClickListener {

    private static final String TAG = "newTravel:Stations";
    private ArrayAdapter<String> adapter;

    private Bundle arguments;
    static Boolean calledAlready = false;
    AutoCompleteTextView to, from;



    public Railchat_New_Travel_Stations() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        arguments = getArguments();

        return inflater.inflate(R.layout.fragment_railchat_new_travel_stations, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();


        Button b = (Button) getActivity().findViewById(R.id.new_travel_stations_button_next);
        b.setOnClickListener(this);

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, Railchat_New_Travel.stations.stations);
        adapter.setNotifyOnChange(true);

        to = (AutoCompleteTextView) getView().findViewById(R.id.new_travel_auto_textview_from);
        to.setOnClickListener(this);
        to.setThreshold(2);
        to.setAdapter(adapter);
        to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stationsEntered();
            }
        });

        from = (AutoCompleteTextView) getView().findViewById(R.id.new_travel_auto_textview_to);
        from.setOnClickListener(this);
        from.setThreshold(2);
        from.setAdapter(adapter);
        from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stationsEntered();
            }
        });
    }

    @Override
    public void onClick(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Station");
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        final AutoCompleteTextView to = (AutoCompleteTextView)getView().findViewById(R.id.new_travel_auto_textview_to);
        final AutoCompleteTextView from = (AutoCompleteTextView)getView().findViewById(R.id.new_travel_auto_textview_from);

        switch (view.getId()){

            case R.id.new_travel_stations_button_next:{

                if (stationsEntered() && from.getText().toString().trim().length() != 0 && to.getText().toString().trim().length() != 0){

                    String f = from.getText().toString();
                    String t = to.getText().toString();

                    arguments.putString("from", f);
                    arguments.putString("to", t);

                    String keyFrom = Railchat_New_Travel.stations.getKey(f);
                    String keyTo = Railchat_New_Travel.stations.getKey(t);

                    arguments.putString("keyFrom", keyFrom);
                    arguments.putString("keyTo", keyTo);

                    Fragment fragment = new Railchat_New_Travel_Train();
                    fragment.setArguments(arguments);
                    ft.replace(R.id.new_travel_frameLayout_fragment, fragment);
                    ft.commit();
                }
                else {
                    Toast.makeText(getActivity(), R.string.message_no_station_picked, Toast.LENGTH_LONG).show();
                }
            }break;
            default:{
                Log.e(TAG, "unexpected Id encountered");
            }break;

        }
    }


    private boolean stationsEntered(){

        boolean right = true;

        if(to.getText().length() != 0){

            if (Railchat_New_Travel.stations.contains(to.getText().toString())){
                to.setTextColor(getResources().getColor(R.color.right_black));
            }
            else {
                to.setTextColor(getResources().getColor(R.color.false_red));
                right = false;
            }

        }

        if (from.getText().length() != 0){
            if (Railchat_New_Travel.stations.contains(from.getText().toString())){
                from.setTextColor(getResources().getColor(R.color.right_black));
            }
            else {
                from.setTextColor(getResources().getColor(R.color.false_red));
                right =  false;
            }
        }

        return right;
    }



}
