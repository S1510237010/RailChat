package at.fhooe.mc.android.travel;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import at.fhooe.mc.android.R;


public class Railchat_New_Travel_Date extends Fragment implements View.OnClickListener {

    private static final String TAG = "newTravel:Date";


    public Railchat_New_Travel_Date() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_railchat_new_travel_date, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        EditText et = (EditText) getView().findViewById(R.id.new_travel_textEdit_date);
        et.setOnClickListener(this);

        Button b = (Button) getView().findViewById(R.id.new_travel_date_button_next);
        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        switch (view.getId()){

            case R.id.new_travel_textEdit_date:{
                EditText text = (EditText) view.findViewById(R.id.new_travel_textEdit_date);
                DateDialog dialog = new DateDialog(text);
                dialog.show(ft, "DatePicker");
                Button b = (Button)getView().findViewById(R.id.new_travel_date_button_next);

            }break;
            case R.id.new_travel_date_button_next:{

                EditText et = (EditText) getView().findViewById(R.id.new_travel_textEdit_date);

                if (et.getText().toString().trim().length() != 0){

                    Bundle bundle = new Bundle();
                    bundle.putString("date", et.getText().toString());

                    Fragment fragment = new Railchat_New_Travel_Stations();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.new_travel_frameLayout_fragment, fragment);
                    ft.commit();
                }
                else {
                    Toast.makeText(getActivity(), R.string.message_no_date_picked, Toast.LENGTH_LONG).show();
                }

            }break;
            default:{
                Log.e(TAG, "unexpected ID encountered");
            }break;

        }
    }
}
