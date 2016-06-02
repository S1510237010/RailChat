package at.fhooe.mc.android;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class railchat_new_travel_date extends Fragment implements View.OnClickListener {

    private static final String TAG = "newTravel:Date";


    public railchat_new_travel_date() {
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

        Button b = (Button) getView().findViewById(R.id.new_travel_date_button_back);
        b.setOnClickListener(this);

        b = (Button) getView().findViewById(R.id.new_travel_date_button_next);
        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (view.getId()){

            case R.id.new_travel_textEdit_date:{
                DateDialog dialog = new DateDialog(view);
                dialog.show(ft, "DatePicker");
                Button b = (Button)getView().findViewById(R.id.new_travel_date_button_next);

            }break;
            case R.id.new_travel_date_button_next:{

                EditText et = (EditText) getView().findViewById(R.id.new_travel_textEdit_date);

                if (et.getText().toString().trim().length() != 0){
                    Fragment fragment = new railchat_new_travel_stations();
                    ft.replace(R.id.new_travel_frameLayout_fragment, fragment);
                    ft.commit();
                }
                else {
                    Toast.makeText(getActivity(), R.string.message_no_date_picked, Toast.LENGTH_LONG).show();
                }

            }break;
            case R.id.new_travel_date_button_back: {
               getActivity().finish();
            }break;
            default:{
                Log.e(TAG, "unexpected ID encountered");
            }break;

        }
    }
}
