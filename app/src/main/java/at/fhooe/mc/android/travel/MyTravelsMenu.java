package at.fhooe.mc.android.travel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.google.firebase.database.DatabaseReference;
import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.GetUser;
import at.fhooe.mc.android.main_menu.MainMenu;
import at.fhooe.mc.android.main_menu.Railchat_Main_Menu;

/**
 * This class is the MainActivity for the TravelsMenu, which is called, when in the Drawer the Option
 * "My Travels" is selected.
 * It has certain Fragments, which are inflated to a certain action from the user.
 * Fragments:
 *  - MyTravelsMenu_ListFragment
 *  - TravelEdit
 *  - TravelOverview
 * And one Activity, which is started, when the user clicks on the FloatingActionButton:
 *  - Raichat_New_Travel
 */
public class MyTravelsMenu extends MainMenu {

    private static final String TAG = "Railchat:myTravels";
    public static DatabaseReference myRef, trainRef;
    public static String userID;
    public static boolean deleteAction;
    public static FloatingActionButton fab;
    public static String fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = new GetUser().getUserID();
        setContentView(R.layout.activity_my_travels_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.myTravels_FloatingButton);



        myRef = Railchat_Main_Menu.database.getDatabase().getReference("Users");

        trainRef = Railchat_Main_Menu.database.getDatabase().getReference("Travels");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.myTravels_frameLayout, new MyTravelsMenu_ListFragment());
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        switch (fragment){

            case "Overview":{
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.myTravels_frameLayout, new MyTravelsMenu_ListFragment());
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
