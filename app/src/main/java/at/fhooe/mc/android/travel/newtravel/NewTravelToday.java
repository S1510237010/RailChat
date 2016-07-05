package at.fhooe.mc.android.travel.newtravel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;

import at.fhooe.mc.android.R;

/**
 * This class is a Fragment subclass of the Activity MainMenu.
 * Here the user can log in a train for today.
 */
public class NewTravelToday extends Fragment implements View.OnClickListener {

    public NewTravelToday() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_travel_today, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        Button b = (Button)getView().findViewById(R.id.new_travel_today_logIn);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.new_travel_today_logIn:{

                StringBuffer date = new StringBuffer();
                Calendar calendar = Calendar.getInstance();
                date.append(calendar.get(Calendar.DAY_OF_MONTH));
                date.append("-");
                date.append((calendar.get(Calendar.MONTH) + 1));
                date.append("-");
                date.append(calendar.get(Calendar.YEAR));

                Intent i = new Intent(getContext(), NewTravel.class);
                i.putExtra("date", date.toString());
                startActivity(i);
            }break;
        }


    }
}
