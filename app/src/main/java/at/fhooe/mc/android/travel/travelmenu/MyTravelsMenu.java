package at.fhooe.mc.android.travel.travelmenu;

import android.os.Bundle;

import com.getbase.floatingactionbutton.FloatingActionButton;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.database.DatabaseReference;
import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.GetStations;
import at.fhooe.mc.android.database.GetUser;
import at.fhooe.mc.android.main_menu.MainMenu;

/**
 * This class is the MainActivity for the TravelsMenu, which is called, when in the Drawer the Option
 * "My Travels" is selected.
 * It has certain Fragments, which are inflated to a certain action from the user.
 * Fragments:
 *  - MyTravelsMenuListFragment
 *  - TravelEdit
 *  - TravelOverview
 * And one Activity, which is started, when the user clicks on the FloatingActionButton:
 *  - Raichat_New_Travel
 */
public class MyTravelsMenu extends AppCompatActivity {

    private static final String TAG = "Railchat:myTravels";
    public static DatabaseReference myRef, trainRef;
    public static String userID;
    public static GetStations stations;
    public static FloatingActionsMenu fab_menu;
    public static FloatingActionButton fab_add, fab_delete;
    public static String fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = new GetUser().getUserID();
        setContentView(R.layout.activity_my_travels_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab_menu = (FloatingActionsMenu) findViewById(R.id.myTravels_fabMenu);
        fab_add = (FloatingActionButton) findViewById(R.id.myTravels_FloatingButton);
        fab_delete = (FloatingActionButton) findViewById(R.id.myTravels_delete);

        myRef = MainMenu.database.getDatabase().getReference("Users");
        trainRef = MainMenu.database.getDatabase().getReference("Travels");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.myTravels_frameLayout, new MyTravelsMenuListFragment());
        fragmentTransaction.commit();

        stations = new GetStations(this);
    }

    @Override
    public void onBackPressed() {
        switch (fragment){

            case "Overview":{
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.myTravels_frameLayout, new MyTravelsMenuListFragment());
                fragmentTransaction.commit();
            }break;
            case "Edit":{
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.myTravels_frameLayout, new TravelOverview());
                fragmentTransaction.commit();
            }break;
            case "List":{
                this.finish();
            }break;
            default:{
                this.finish();
            }
        }

    }
}
