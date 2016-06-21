package at.fhooe.mc.android.travel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.InitializeDatabase;
import at.fhooe.mc.android.main_menu.Railchat_Main_Menu;


public class Railchat_New_Travel_Save extends Fragment implements View.OnClickListener {

    Bundle data;
    String date, to, from, train, time;
    public DatabaseReference myRef_Travel;
    String userID = "8c03c4dd-17b1-42aa-af94-e7846cb5049c";

    public Railchat_New_Travel_Save() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        data = getArguments();
        myRef_Travel = Railchat_Main_Menu.database.getDatabase().getReference();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_railchat__new__travel__save, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        date = data.getString("date");
        from = data.getString("from");
        to = data.getString("to");
        train = data.getString("train");
        time = data.getString("time");

        TextView et = (TextView)getActivity().findViewById(R.id.new_travel_save_date_item);
        et.setText(date);

        et = (TextView)getActivity().findViewById(R.id.new_travel_save_from_item);
        et.setText(from);

        et = (TextView)getActivity().findViewById(R.id.new_travel_save_to_item);
        et.setText(to);

        et = (TextView)getActivity().findViewById(R.id.new_travel_save_train_item);
        et.setText(train);

        Button b = (Button)getActivity().findViewById(R.id.new_travel_save_button_save);
        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_travel_save_button_save:{

                String key = myRef_Travel.push().getKey();

                Travel myTravel = new Travel(to, from, train, date, time);
                Map<String, Object> travelValues = myTravel.toMap();
                Map<String, Object> traveler = new HashMap<>();
                traveler.put(key, userID);

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/User/" + userID + "/" + key, travelValues);
                childUpdates.put("/RailJets/" + train + "/Traveler/" + date + "/", traveler);

                myRef_Travel.updateChildren(childUpdates);
                Toast.makeText(getActivity(), "Travel was successfully added", Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }break;
            default:{}break;
        }
    }


    private class Travel {

        public String to;
        public String from;
        public String train;
        public String date;
        public String time;

        public Travel(){

        }
        public Travel(String to, String from, String train, String date, String time){
            this.to = to;
            this.from = from;
            this.train = train;
            this.date = date;
            this.time = time;
        }


        public Map<String, Object> toMap(){

            HashMap<String, Object> result = new HashMap<>();
            result.put("To", to);
            result.put("From", from);
            result.put("Train", train);
            result.put("Date", date);
            result.put("Time", time);

            return result;
        }


    }

}
