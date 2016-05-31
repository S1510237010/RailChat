package at.fhooe.mc.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class railchat_new_travel extends Activity {

    private static final String TAG = "new Travel";
    private String userID;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railchat_new_travel);




        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new railchat_new_travel_date();
        fragmentTransaction.add(R.id.new_travel_frameLayout_fragment, fragment);
        fragmentTransaction.commit();


//        myRef_Station = database.getReference("Stations");
//        myRef_Station.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> stations = dataSnapshot.getChildren();
//                Iterator<DataSnapshot> iterator = stations.iterator();
//
//                while(iterator.hasNext()){
//                    DataSnapshot child = iterator.next();
//                    railchat_new_travel.stations.add(child.child("Name").getValue().toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });


//        EditText edit = (EditText) findViewById(R.id.new_travel_editText_date);
//        edit.setOnClickListener(this);
//
//        edit = (EditText)findViewById(R.id.new_travel_editText_from);
//        edit.setOnClickListener(this);
//
//        edit = (EditText)findViewById(R.id.new_travel_editText_to);
//        edit.setOnClickListener(this);
//
//        edit = (EditText)findViewById(R.id.new_travel_editText_train);
//        edit.setOnClickListener(this);

    }



//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Pick Station");
//
//        EditText edit = (EditText) findViewById(R.id.new_travel_editText_from);
//        ArrayList<String> list = new ArrayList<String>(stations.size());
//        for (String s : stations){
//            list.add(new String(s));
//        }
//
//        if (edit.getText() != null && edit.getText().toString() != ""){
//            list.remove(edit.getText().toString());
//            index_from = stations.indexOf(edit.getText().toString());
//        }
//
//        edit = (EditText) findViewById(R.id.new_travel_editText_to);
//        if (edit.getText() != null && edit.getText().toString() != ""){
//            list.remove(edit.getText().toString());
//            index_to = stations.indexOf(edit.getText().toString());
//        }
//
//        if (index_from != -1 && index_to != -1){
//            if ((index_from - index_to) < 0){
//                even = -1;
//            }
//            else {
//                even = 1;
//            }
//        }
//        System.out.println("even: " + even);
//
//        switch (view.getId()){
//            case R.id.new_travel_editText_date:{
//
//                DateDialog dialog = new DateDialog(view);
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                dialog.show(ft, "DatePicker");
//
//            }break;
//
//            case R.id.new_travel_editText_from: {
//
//                AlertDialog dialog;
//                final CharSequence[] items = list.toArray(new CharSequence[list.size()]);
//                final EditText from = (EditText)findViewById(view.getId());
//
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int pos) {
//                        from.setText(items[pos]);
//                    }
//                });
//                dialog = builder.create();
//                dialog.show();
//            }break;
//
//            case R.id.new_travel_editText_to: {
//
//                AlertDialog dialog;
//                final CharSequence[] items = list.toArray(new CharSequence[list.size()]);
//                final EditText from = (EditText)findViewById(view.getId());
//
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int pos) {
//                        from.setText(items[pos]);
//                    }
//                });
//                dialog = builder.create();
//                dialog.show();
//            }break;
//
//            case R.id.new_travel_editText_train: {
//
//                final CharSequence[] items = trains.toArray(new CharSequence[trains.size()]);
//                final EditText from = (EditText)findViewById(view.getId());
//
//                new DownloadTrains(this).execute();
//
//                AlertDialog dialog;
//                builder.setTitle("Pick Train");
//
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int pos) {
//                        from.setText(items[pos]);
//                    }
//                });
//                dialog = builder.create();
//                dialog.show();
//            }break;
//        }
    }


//    private class DownloadTrains{
//
//        private final ArrayList<String> railjets = new ArrayList<String>();
//
//        protected ArrayList<String> getRailjets(String... strings) {
//
//            System.out.println("doInBackground");
//            final int finalEven = even;
//            final int finalIndex_from = index_from;
//            final int finalIndex_to = index_to;
//
//            myRef_RJ.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Iterable<DataSnapshot> snapshot_Train = dataSnapshot.getChildren();
//                    Iterator<DataSnapshot> iterator = snapshot_Train.iterator();
//
//                    while (iterator.hasNext()) {
//                        DataSnapshot child = iterator.next();
//
//                        if (child != null) {
//
//                            boolean hasStation = false;
//                            int trainNumber = Integer.parseInt(child.getKey());
//
//                            if (finalEven == 1 && trainNumber % 2 == 0){
//
//                                Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
//                                Iterator<DataSnapshot> iterator_station = stations.iterator();
//
//                                boolean to = false, from = false;
//
//                                while(iterator_station.hasNext()){
//
//                                    DataSnapshot childTrain = iterator_station.next();
//
//                                    if (childTrain != null){
//                                        int stationNumber = Integer.parseInt(childTrain.getValue().toString());
//
//                                        if (stationNumber == finalIndex_from){
//                                            from = true;
//                                        }
//
//                                        if (stationNumber == finalIndex_to){
//                                            to = true;
//                                        }
//                                    }
//
//                                }
//
//                                if (from && to){
//                                    hasStation = true;
//                                }
//
//                            }
//                            else if (finalEven == -1 && trainNumber % 2 == 1){
//
//                                Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
//                                Iterator<DataSnapshot> iterator_station = stations.iterator();
//
//                                boolean to = false, from = false;
//
//                                while(iterator_station.hasNext()){
//                                    DataSnapshot childTrain = iterator_station.next();
//
//                                    if (childTrain != null){
//                                        int stationNumber = Integer.parseInt(childTrain.getValue().toString());
//
//                                        if (stationNumber == finalIndex_from){
//                                            from = true;
//                                        }
//
//                                        if (stationNumber == finalIndex_to){
//                                            to = true;
//                                        }
//                                    }
//                                }
//
//                                if (from && to){
//                                    hasStation = true;
//                                }
//                            }
//                            else if (finalEven == 0){
//
//                                System.out.println(trainNumber);
//
//                                Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
//                                Iterator<DataSnapshot> iterator_station = stations.iterator();
//
//                                boolean to = false, from = false;
//
//                                if (finalIndex_from == -1){
//                                    from = true;
//                                }
//                                if (finalIndex_to == -1){
//                                    to = true;
//                                }
//
//                                while(iterator_station.hasNext()){
//                                    DataSnapshot childTrain = iterator_station.next();
//
//                                    if (childTrain != null){
//                                        int stationNumber = Integer.parseInt(childTrain.getValue().toString());
//
//                                        if (stationNumber == finalIndex_from){
//                                            from = true;
//                                        }
//
//                                        if (stationNumber == finalIndex_to){
//                                            to = true;
//                                        }
//                                    }
//                                }
//
//                                if (from && to){
//                                    hasStation = true;
//                                }
//                            }
//
//                            if (hasStation){
//                                railjets.add(child.getKey());
//                            }
//                        }
//                    }
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                }
//            });
//            return railjets;
//        }
//

