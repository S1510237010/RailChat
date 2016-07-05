package at.fhooe.mc.android.travel.newtravel;

import android.app.AlertDialog;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import at.fhooe.mc.android.R;

/**
 * This class is a Fragment of NewTravel.
 * The user can pick two stations in two AutocompleteTextViews.
 */
public class NewTravelStations extends Fragment implements View.OnClickListener {

    private static final String TAG = "newTravel:Stations";
    private ArrayAdapter<String> adapter;

    private Bundle arguments;
    static Boolean calledAlready = false;
    AutoCompleteTextView to, from;



    public NewTravelStations() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        arguments = getArguments();

        return inflater.inflate(R.layout.fragment_railchat_new_travel_stations, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();


        Button b = (Button) getActivity().findViewById(R.id.new_travel_stations_button_next);
        b.setOnClickListener(this);

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, NewTravel.stations.stations);
        adapter.setNotifyOnChange(true);

        to = (AutoCompleteTextView) getView().findViewById(R.id.new_travel_auto_textview_to);
        to.setOnClickListener(this);
        to.setThreshold(2);
        to.setAdapter(adapter);
        to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stationsEntered(to);
            }
        });

        from = (AutoCompleteTextView) getView().findViewById(R.id.new_travel_auto_textview_from);
        from.setOnClickListener(this);
        from.setThreshold(2);
        from.setAdapter(adapter);
        from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stationsEntered(from);
            }
        });
    }

    @Override
    public void onClick(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Station");
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        final AutoCompleteTextView to = (AutoCompleteTextView)getView().findViewById(R.id.new_travel_auto_textview_to);
        final AutoCompleteTextView from = (AutoCompleteTextView)getView().findViewById(R.id.new_travel_auto_textview_from);

        switch (view.getId()){

            case R.id.new_travel_stations_button_next:{

                if (stationsEntered(to) && stationsEntered(from) && from.getText().toString().trim().length() != 0 && to.getText().toString().trim().length() != 0){

                    String f = from.getText().toString();
                    String t = to.getText().toString();

                    arguments.putString("from", f);
                    arguments.putString("to", t);

                    String keyFrom = NewTravel.stations.getKey(f);
                    String keyTo = NewTravel.stations.getKey(t);

                    arguments.putString("keyFrom", keyFrom);
                    arguments.putString("keyTo", keyTo);

                    Fragment fragment = new NewTravelTrain();
                    fragment.setArguments(arguments);
                    ft.replace(R.id.new_travel_frameLayout_fragment, fragment);
                    ft.commit();
                }
                else {
                    Toast.makeText(getActivity(), R.string.message_no_station_picked, Toast.LENGTH_LONG).show();
                }
            }break;
            default:{
                Log.e(TAG, "unexpected Id encountered");
            }break;

        }
    }

    /**
     * This method controls if there are two different stations entered.
     * @return      true == different text, false == same text or one textview is empty
     */
    private boolean sameStation(){

        if (to.getText().length() == 0 || from.getText().length() == 0){
            return false;
        }

        if (to.getText().toString().equals(from.getText().toString())){
            return true;
        }
        return false;
    }

    /**
     * This method controls if there are two legit stations entered.
     * If it is legit, the text color will be black, in other case it will be red.
     * @param textView  textview which text should be controlled
     * @return          true == legit Input, false == false input
     */
    private boolean stationsEntered(AutoCompleteTextView textView){

        boolean right = true;

        if(textView.getText().length() != 0) {

            if (!sameStation() && NewTravel.stations.contains(textView.getText().toString())) {
                textView.setTextColor(getResources().getColor(R.color.right_black));
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                textView.setTextColor(getResources().getColor(R.color.false_red));
                right = false;
            }

        }

        return right;
    }



}
