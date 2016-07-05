package at.fhooe.mc.android.travel.travelmenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.getbase.floatingactionbutton.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.GetStations;
import at.fhooe.mc.android.travel.newtravel.NewTravel;
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
    AutoCompleteTextView to, from;
    private ArrayAdapter<String> adapter;
    private static GetStations stations;


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


            to = (AutoCompleteTextView) getView().findViewById(R.id.travel_edit_to);
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

            from = (AutoCompleteTextView) getView().findViewById(R.id.travel_edit_from);
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
