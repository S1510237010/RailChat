package at.fhooe.mc.android.travel.newtravel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.GetUser;
import at.fhooe.mc.android.main_menu.MainMenu;

/**
 * This class gives a summary of the travel, which the user wants to save and the
 * option to save the travel.
 * When this option is activated, it sends the travel to the database and saved it there.
 */
public class NewTravelSave extends Fragment implements View.OnClickListener {

    Bundle data;
    String date, to, from, train, time, persons;
    public DatabaseReference myRef_Travel;
    String userID;

    public NewTravelSave() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        userID = new GetUser().getUserID();
        data = getArguments();
        myRef_Travel = MainMenu.database.getDatabase().getReference();

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

                String key = myRef_Travel.child("Travels").push().getKey();

                Travel myTravel = new Travel(to, from, train, date, time, persons);
                Map<String, Object> travelValues = myTravel.toMap();
                Map<String, Object> traveler = new HashMap<>();
                traveler.put(key, userID);

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/Users/" + userID + "/Travels/" + key, key);
                childUpdates.put("/Travels/" + key, travelValues);
                childUpdates.put("/RailjetTraveler/" + date + "/" + train + "/", traveler);

                myRef_Travel.updateChildren(childUpdates);
                Toast.makeText(getActivity(), "Travel was successfully added", Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }break;
            default:{}break;
        }
    }


    /**
     * This class has all fields of a Travelitem:
     * String:
     * - to     : Station, to where the travel is
     * - from   : Station, from where the travel starts
     * - train  : Number of the Train, which will be taken
     * - date   : Date of the Travel
     * - time   : Time of the Travel ( at the moment not used, because we have no information about the time
     *            of the trains )
     * - persons: Persons in the Train, but this is not used yet, it will be delete maybe, at the moment
     *            in every case it will be 0.
     */
    private class Travel {

        public String to;
        public String from;
        public String train;
        public String date;
        public String time;
        public String persons;

        public Travel(String to, String from, String train, String date, String time, String persons){
            this.to = to;
            this.from = from;
            this.train = train;
            this.date = date;
            this.time = time;
            this.persons = persons;
        }

        /**
         * Makes out of a Travel Object a Map with a String key and a Object.
         * Object is only used in case that later a other Object must be added to the Travel class.
         * @return  Map with a String key and a Object to which the key points, all Travel fields are put there.
         */
        public Map<String, Object> toMap(){

            HashMap<String, Object> result = new HashMap<>();
            result.put("To", to);
            result.put("From", from);
            result.put("Train", train);
            result.put("Date", date);
            result.put("Time", time);
            result.put("Persons", persons);

            return result;
        }


    }

}
