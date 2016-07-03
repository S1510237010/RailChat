package at.fhooe.mc.android.travel;

import android.content.Intent;
import android.os.Bundle;
import com.getbase.floatingactionbutton.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.fhooe.mc.android.R;


public class TravelOverview extends Fragment {


    private FloatingActionButton fab, delete;
    TravelListItem item;


    public TravelOverview() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fab = MyTravelsMenu.fab_add;
        this.delete = MyTravelsMenu.fab_delete;
        item = MyTravelsMenu_ListFragment.toDelete;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_overview, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        fab.setImageResource(R.drawable.ic_create_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.myTravels_frameLayout, new TravelEdit());
                fragmentTransaction.commit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteTravel(item);
                Intent i = new Intent(getContext(), MyTravelsMenu.class);
                startActivity(i);
            }
        });

        MyTravelsMenu.fragment = "Overview";


        if (item != null){

            TextView tv = (TextView)getView().findViewById(R.id.overview_station_to);
            tv.setText(item.getTo());

            tv = (TextView)getView().findViewById(R.id.overview_station_from);
            tv.setText(item.getFrom());

            tv = (TextView)getView().findViewById(R.id.overview_date);
            tv.setText(item.getDate());

            tv = (TextView)getView().findViewById(R.id.overview_time);
            tv.setText(item.getTime());

            tv = (TextView)getView().findViewById(R.id.overview_personNumber);
            tv.setText(String.valueOf(item.getPersons()));

            tv = (TextView)getView().findViewById(R.id.overview_trainNumber);
            tv.setText(String.valueOf(item.getTrainNumber()));

        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
