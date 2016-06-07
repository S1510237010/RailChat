package at.fhooe.mc.android.travel;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import at.fhooe.mc.android.R;

public class Railchat_New_Travel extends Activity {

    private static final String TAG = "new Travel";
    private String userID;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railchat_new_travel);


        getActionBar().setDisplayShowTitleEnabled(false);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new Railchat_New_Travel_Date();
        fragmentTransaction.add(R.id.new_travel_frameLayout_fragment, fragment);
        fragmentTransaction.commit();

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
            case R.id.back_button:{
                this.finish();
            }break;
            default: {
                Log.e(TAG, "Unexpected id encountered");
            }break;
        }

        return super.onOptionsItemSelected(item);
    }
}
