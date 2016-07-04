package at.fhooe.mc.android.travel.travelmenu;

import android.content.Intent;
import android.os.Bundle;
import com.getbase.floatingactionbutton.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.travel.travellist.DeleteTravel;
import at.fhooe.mc.android.travel.travellist.TravelListItem;
import at.fhooe.mc.android.travel.newtravel.DateDialog;

/**
 * This class is a Fragment subclass of the Activity TravelMenu.
 * Here, the user can edit his Travels.
 * Then it is resaved in the database.
 */
public class TravelEdit extends Fragment implements View.OnClickListener{

    private FloatingActionButton fab, ok;
    TravelListItem item;

    public TravelEdit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = MyTravelsMenuListFragment.toDelete;
        fab = MyTravelsMenu.fab_delete;
        ok = MyTravelsMenu.fab_add;

    }


    @Override
    public void onStart() {
        super.onStart();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteTravel(item);
                Intent i = new Intent(getContext(), MyTravelsMenu.class);
                startActivity(i);
            }
        });

        ok.setImageResource(R.drawable.checked);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        MyTravelsMenu.fragment = "Edit";

        if (item != null){

            EditText et = (EditText)getView().findViewById(R.id.travel_edit_to);
            et.setText(item.getTo());
            et.setOnClickListener(this);

            et = (EditText) getView().findViewById(R.id.travel_edit_from);
            et.setText(item.getFrom());
            et.setOnClickListener(this);

            et = (EditText) getView().findViewById(R.id.travel_edit_date);
            et.setText(item.getDate());
            et.setOnClickListener(this);

            et = (EditText) getView().findViewById(R.id.travel_edit_time);
            et.setText(item.getTime());
            et.setOnClickListener(this);

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


    @Override
    public void onClick(View v) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        switch (v.getId()){
            case R.id.travel_edit_date:{
                EditText text = (EditText) v.findViewById(R.id.travel_edit_date);
                DateDialog dialog = new DateDialog(text);
                dialog.show(ft, "DatePicker");
            }break;



        }
    }
}
