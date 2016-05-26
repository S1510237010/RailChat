package at.fhooe.mc.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;

public class railchat_my_travels_menue extends Fragment {
    private static final String TAG = "Railchat:myTravels";
    private Firebase database;


    public railchat_my_travels_menue() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        database = new Firebase("https://railchat.firebaseio.com/");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_railchat_my_travels_menue, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_and_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()){
            case R.id.add_button:{
                i = new Intent(getActivity(), railchat_new_travel.class);
            }break;
            default: {
                Log.e(TAG, "unexpected option item");} break;
        }

        if (i != null){
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
