package at.fhooe.mc.android.at.fhhoe.mc.android.travel;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Railchat_New_Travel_Train extends Fragment implements View.OnClickListener {

    private static final String TAG = "newTravel:Train";
    private final ArrayList<String> railjets = new ArrayList<String>();
    private FirebaseDatabase database;
    public DatabaseReference myRef_RJ;
    String to, from;
    int keyTo, keyFrom;
    Bundle bundle;

    public Railchat_New_Travel_Train() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();
        myRef_RJ = database.getReference("RailJets");

        bundle = getArguments();
        String from = bundle.getString("from");
        String to = bundle.getString("to");
        keyTo = bundle.getInt("keyTo");
        keyFrom = bundle.getInt("keyFrom");

        new DownloadTrains().execute();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_railchat_new_travel_train, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        EditText et = (EditText) getActivity().findViewById(R.id.new_travel_editText_train);
        et.setOnClickListener(this);


        Button b = (Button) getView().findViewById(R.id.new_travel_train_button_next);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Railjet");

        switch (view.getId()){

            case R.id.new_travel_train_button_next:{

                EditText et = (EditText) getActivity().findViewById(R.id.new_travel_editText_train);

                if (et.getText().toString().trim().length() == 0){
                    Toast.makeText(getActivity(), "Please pick train", Toast.LENGTH_SHORT).show();
                }
                else {
                    bundle.putString("train", et.getText().toString());

                    Fragment fragment = new Railchat_New_Travel_Save();
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.new_travel_frameLayout_fragment, fragment);
                    ft.commit();
                }

            }break;
            case R.id.new_travel_editText_train:{
                if (railjets.size() == 0){
                    Toast.makeText(getActivity(), "Loading.. please wait", Toast.LENGTH_SHORT).show();
                }
                else {
                    final CharSequence[] items = railjets.toArray(new CharSequence[railjets.size()]);
                    final EditText from = (EditText)getActivity().findViewById(view.getId());

                    AlertDialog dialog;
                    builder.setTitle("Pick Train");

                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int pos) {
                            from.setText(items[pos]);
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            }break;
            default:{}break;

        }
    }


    private class DownloadTrains extends AsyncTask<String, Integer, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {

            System.out.println("doInBackground");

            final int finalEven = keyFrom - keyTo;
            final int finalIndex_from = keyFrom;
            final int finalIndex_to = keyTo;

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

                            if (finalEven > 0 && trainNumber % 2 == 0){

                                Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
                                Iterator<DataSnapshot> iterator_station = stations.iterator();

                                boolean to = false, from = false;

                                while(iterator_station.hasNext()){

                                    DataSnapshot trainstation = iterator_station.next();

                                    if (trainstation != null){
                                        int stationNumber = Integer.parseInt(trainstation.getValue().toString());

                                        if (stationNumber == finalIndex_from){
                                            from = true;
                                        }

                                        if (stationNumber == finalIndex_to){
                                            to = true;
                                        }
                                    }

                                }

                                if (from && to){
                                    hasStation = true;
                                }

                            }
                            else if (finalEven < 0 && trainNumber % 2 == 1){

                                Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
                                Iterator<DataSnapshot> iterator_station = stations.iterator();

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
                                    hasStation = true;
                                }
                            }
                            else if (finalEven == 0){

                                System.out.println(trainNumber);

                                Iterable<DataSnapshot> stations = child.child("StationNumber").getChildren();
                                Iterator<DataSnapshot> iterator_station = stations.iterator();

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
                                    hasStation = true;
                                }
                            }

                            if (hasStation){
                                railjets.add(child.getKey());
                            }
                        }
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
