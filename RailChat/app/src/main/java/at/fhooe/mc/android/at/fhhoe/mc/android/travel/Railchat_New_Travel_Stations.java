package at.fhooe.mc.android.at.fhhoe.mc.android.travel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import at.fhooe.mc.android.R;


public class Railchat_New_Travel_Stations extends Fragment implements View.OnClickListener {

    private static final String TAG = "newTravel:Stations";
    private ArrayList<String> stations = new ArrayList<String>();
    private ArrayList<String> stationKeys = new ArrayList<String>();
    private FirebaseDatabase database;
    public DatabaseReference myRef_Station;
    private int keyFrom, keyTo;
    private Bundle arguments;
    static Boolean calledAlready = false;



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

        database = FirebaseDatabase.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();

        new DownloadStations(getActivity()).execute();

        EditText edit = (EditText)getView().findViewById(R.id.new_Travel_editText_stations_to);
        edit.setOnClickListener(this);

        edit = (EditText)getView().findViewById(R.id.new_travel_editText_stations_from);
        edit.setOnClickListener(this);

        Button b = (Button)getActivity().findViewById(R.id.new_travel_stations_button_next);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Station");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        final EditText to = (EditText)getActivity().findViewById(R.id.new_Travel_editText_stations_to);
        final EditText from = (EditText)getActivity().findViewById(R.id.new_travel_editText_stations_from);


        switch (view.getId()){

            case R.id.new_travel_editText_stations_from:{

                if (stations.size() != 0) {

                    AlertDialog dialog;
                    final CharSequence[] items = stations.toArray(new CharSequence[stations.size()]);
                    final CharSequence[] itemKeys = stationKeys.toArray(new CharSequence[stationKeys.size()]);

                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int pos) {
                            from.setText(items[pos]);
                            keyFrom = Integer.parseInt(itemKeys[pos].toString());
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(getActivity(), "Loading.. please wait", Toast.LENGTH_SHORT).show();
                }

            }break;
            case R.id.new_Travel_editText_stations_to:{
                if (stations.size() == 0){
                    Toast.makeText(getActivity(), "Loading.. please wait", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog dialog;
                    final CharSequence[] items = stations.toArray(new CharSequence[stations.size()]);
                    final CharSequence[] itemKeys = stationKeys.toArray(new CharSequence[stationKeys.size()]);

                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int pos) {
                            to.setText(items[pos]);
                            keyTo = Integer.parseInt(itemKeys[pos].toString());
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            }break;
            case R.id.new_travel_stations_button_next:{

                if (from.getText().toString().trim().length() != 0 && to.getText().toString().trim().length() != 0){

                    arguments.putString("from", from.getText().toString());
                    arguments.putString("to", to.getText().toString());
                    arguments.putInt("keyFrom", keyFrom);
                    arguments.putInt("keyTo", keyTo);

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


    private class DownloadStations extends AsyncTask<String, Integer, Boolean> {

        ProgressDialog dialog;
        Activity activity;
        Context context;

        public DownloadStations(Activity activity){
            this.activity = activity;
            this.context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            myRef_Station = database.getReference("Stations");
            myRef_Station.keepSynced(true);
            myRef_Station.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> stationsIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = stationsIterable.iterator();

                    while(iterator.hasNext()){
                        DataSnapshot station = iterator.next();
                        stations.add(station.child("Name").getValue().toString());
                        stationKeys.add(station.getKey());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            return true;
        }



    }


}
