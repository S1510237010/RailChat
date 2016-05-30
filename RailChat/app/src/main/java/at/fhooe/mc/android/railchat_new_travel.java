package at.fhooe.mc.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class railchat_new_travel extends Activity implements View.OnClickListener{

    private static final String TAG = "new Travel";
    private String userID;

    private Spinner spinner;
    private FirebaseDatabase database;
    public DatabaseReference myRef_Station, myRef_RJ;
    private static ArrayList<String> stations, trains;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railchat_new_travel);
        stations = new ArrayList<String>();
        trains = new ArrayList<String>();

        Log.i(TAG, "onCreate");


        database = FirebaseDatabase.getInstance();
        myRef_RJ = database.getReference("RailJets");
        myRef_Station = database.getReference("Stations");
        myRef_Station.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> stations = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = stations.iterator();

                while(iterator.hasNext()){
                    DataSnapshot child = iterator.next();
                    railchat_new_travel.stations.add(child.child("Name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        userID = "8c03c4dd-17b1-42aa-af94-e7846cb5049c";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stations);
        adapter.notifyDataSetChanged();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        EditText edit = (EditText) findViewById(R.id.new_travel_editText_date);
        edit.setOnClickListener(this);

        edit = (EditText)findViewById(R.id.new_travel_editText_from);
        edit.setOnClickListener(this);

        edit = (EditText)findViewById(R.id.new_travel_editText_to);
        edit.setOnClickListener(this);

        edit = (EditText)findViewById(R.id.new_travel_editText_train);
        edit.setOnClickListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

    }

    @Override
    public void onClick(View view) {

        int index_to = -1;
        int index_from = -1;
        int even = 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Station");

        EditText edit = (EditText) findViewById(R.id.new_travel_editText_from);
        ArrayList<String> list = new ArrayList<String>(stations.size());
        for (String s : stations){
            list.add(new String(s));
        }

        if (edit.getText() != null && edit.getText().toString() != ""){
            list.remove(edit.getText().toString());
            index_from = stations.indexOf(edit.getText().toString());
        }

        edit = (EditText) findViewById(R.id.new_travel_editText_to);
        if (edit.getText() != null && edit.getText().toString() != ""){
            list.remove(edit.getText().toString());
            index_to = stations.indexOf(edit.getText().toString());
        }

        System.out.println("From: " + index_from);
        System.out.println("To: " + index_to);


        if (index_from != -1 && index_to != -1){
            if ((index_from - index_to) < 0){
                even = -1;
            }
            else {
                even = 1;
            }
        }
        System.out.println("even: " + even);

        switch (view.getId()){
            case R.id.new_travel_editText_date:{

                DateDialog dialog = new DateDialog(view);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");

            }break;

            case R.id.new_travel_editText_from: {

                AlertDialog dialog;
                final CharSequence[] items = list.toArray(new CharSequence[list.size()]);
                final EditText from = (EditText)findViewById(view.getId());

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        from.setText(items[pos]);
                    }
                });
                dialog = builder.create();
                dialog.show();
            }break;

            case R.id.new_travel_editText_to: {

                AlertDialog dialog;
                final CharSequence[] items = list.toArray(new CharSequence[list.size()]);
                final EditText from = (EditText)findViewById(view.getId());

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        from.setText(items[pos]);
                    }
                });
                dialog = builder.create();
                dialog.show();
            }break;

            case R.id.new_travel_editText_train: {

                AlertDialog dialog;
                builder.setTitle("Pick Train");
                final int finalEven = even;
                final int finalIndex_from = index_from;
                final int finalIndex_to = index_to;

                myRef_RJ.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> snapshot_Train = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iterator = snapshot_Train.iterator();

                        while (iterator.hasNext()) {
                            DataSnapshot child = iterator.next();

                            if (child != null) {

                                boolean hasStation = false;
                                int trainNumber = Integer.parseInt(child.getKey());

                                if (finalEven == 1 && trainNumber % 2 == 0){

                                    System.out.println(trainNumber);

                                    Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
                                    Iterator<DataSnapshot> iterator_station = stations.iterator();

                                    System.out.println("if");

                                    boolean to = false, from = false;

                                    while(iterator_station.hasNext()){

                                        DataSnapshot childTrain = iterator_station.next();

                                        if (childTrain != null){
                                            int stationNumber = Integer.parseInt(childTrain.getValue().toString());

                                            if (stationNumber == finalIndex_from){
                                                from = true;
                                            }

                                            if (stationNumber == finalIndex_to){
                                                to = true;
                                            }
                                        }

                                    }

                                    if (from && to){
                                        System.out.println("hasStation");
                                        hasStation = true;
                                    }

                                }
                                else if (finalEven == -1 && trainNumber % 2 == 1){

                                    System.out.println(trainNumber);

                                    Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
                                    Iterator<DataSnapshot> iterator_station = stations.iterator();

                                    System.out.println("else if");

                                    boolean to = false, from = false;

                                    while(iterator_station.hasNext()){
                                        DataSnapshot childTrain = iterator_station.next();

                                        if (childTrain != null){
                                            int stationNumber = Integer.parseInt(childTrain.getValue().toString());

                                            if (stationNumber == finalIndex_from){
                                                from = true;
                                            }

                                            if (stationNumber == finalIndex_to){
                                                to = true;
                                            }
                                        }
                                    }

                                    if (from && to){
                                        System.out.println("hasStation");
                                        hasStation = true;
                                    }
                                }
                                else if (finalEven == 0){

                                    System.out.println(trainNumber);

                                    Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
                                    Iterator<DataSnapshot> iterator_station = stations.iterator();

                                    System.out.println("else");

                                    boolean to = false, from = false;

                                    if (finalIndex_from == -1){
                                        from = true;
                                    }
                                    if (finalIndex_to == -1){
                                        to = true;
                                    }

                                    while(iterator_station.hasNext()){
                                        DataSnapshot childTrain = iterator_station.next();

                                        if (childTrain != null){
                                            int stationNumber = Integer.parseInt(childTrain.getValue().toString());

                                            if (stationNumber == finalIndex_from){
                                                from = true;
                                            }

                                            if (stationNumber == finalIndex_to){
                                                to = true;
                                            }
                                        }
                                    }

                                    if (from && to){
                                        System.out.println("hasStation");
                                        hasStation = true;
                                    }
                                }

                                if (hasStation){
                                    System.out.println("Train added");
                                    railchat_new_travel.trains.add(child.getKey());
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                final CharSequence[] items = trains.toArray(new CharSequence[trains.size()]);
                final EditText from = (EditText)findViewById(view.getId());

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        from.setText(items[pos]);
                    }
                });
                dialog = builder.create();
                dialog.show();
            }break;
        }
    }
}
