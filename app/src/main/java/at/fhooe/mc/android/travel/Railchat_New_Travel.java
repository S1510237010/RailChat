package at.fhooe.mc.android.travel;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.GetStations;

/**
 * This class is an Activity. It is started, when in the MyTravelsMenu_Listfragment the FloatingButton
 * is clicked.
 * It consists of a Fragmentcontainer, which is filled of one of this Fragments:
 *  - Railchat_New_Travel_Date
 *  - Rail
 */
public class Railchat_New_Travel extends AppCompatActivity {

    private static final String TAG = "new Travel";

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    protected static GetStations stations;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railchat_new_travel);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new Railchat_New_Travel_Date();
        fragmentTransaction.add(R.id.new_travel_frameLayout_fragment, fragment);
        fragmentTransaction.commit();
        stations = new GetStations(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.railchat_new_travel_menue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            default: {
                Log.e(TAG, "Unexpected id encountered");
            }break;
        }

        return super.onOptionsItemSelected(item);
    }

}
