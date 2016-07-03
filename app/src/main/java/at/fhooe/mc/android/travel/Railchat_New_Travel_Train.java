package at.fhooe.mc.android.travel;


import android.app.AlertDialog;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.GetTrains;
import at.fhooe.mc.android.database.InitializeDatabase;
import at.fhooe.mc.android.main_menu.MainMenu;

/**
 * A simple {@link Fragment} subclass.
 */
public class Railchat_New_Travel_Train extends Fragment implements View.OnClickListener {

    private static final String TAG = "newTravel:Train";
    private ArrayList<String> railjets = new ArrayList<String>();
    public DatabaseReference myRef_RJ;
    String to, from;
    String keyTo, keyFrom;
    Bundle bundle;

    public Railchat_New_Travel_Train() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myRef_RJ = MainMenu.database.getDatabase().getReference("RailJets");

        bundle = getArguments();
        String from = bundle.getString("from");
        String to = bundle.getString("to");
        keyTo = bundle.getString("keyTo");
        keyFrom = bundle.getString("keyFrom");


        railjets = new GetTrains(keyFrom, keyTo, bundle.getString("date")).getTrains();

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
                    bundle.putString("time", "00:00");

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



}
