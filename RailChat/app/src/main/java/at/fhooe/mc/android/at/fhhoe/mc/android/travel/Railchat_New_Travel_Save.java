package at.fhooe.mc.android.at.fhhoe.mc.android.travel;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import at.fhooe.mc.android.R;


public class Railchat_New_Travel_Save extends Fragment implements View.OnClickListener {

    Bundle data;
    private FirebaseDatabase database;
    String date, to, from, train;
    public DatabaseReference myRef_Travel;
    String userID = "8c03c4dd-17b1-42aa-af94-e7846cb5049c";

    public Railchat_New_Travel_Save() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        data = getArguments();
        database = FirebaseDatabase.getInstance();
        myRef_Travel = database.getReference();

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

                Travel myTravel = new Travel(to, from, train, date);
                Map<String, Object> travelValues = myTravel.toMap();
                Map<String, Object> traveler = new HashMap<>();
                traveler.put(key, userID);

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/User/" + userID + "/" + key, travelValues);
                childUpdates.put("/RailJets/" + train + "/Traveler/", traveler);

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

        public Travel(){

        }
        public Travel(String to, String from, String train, String date){
            this.to = to;
            this.from = from;
            this.train = train;
            this.date = date;
        }


        public Map<String, Object> toMap(){

            HashMap<String, Object> result = new HashMap<>();
            result.put("To", to);
            result.put("From", from);
            result.put("Train", train);
            result.put("Date", date);

            return result;
        }


    }

}
