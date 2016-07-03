package at.fhooe.mc.android.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.GetUser;
import at.fhooe.mc.android.login.LoginSplash;
import at.fhooe.mc.android.main_menu.MainMenu;
import at.fhooe.mc.android.settings.SettingsActivity;

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
public class MyTravelsMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        myRef = MainMenu.database.getDatabase().getReference("Users");
        trainRef = MainMenu.database.getDatabase().getReference("Travels");

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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent i;

        switch(item.getItemId()){

            case R.id.nav_timeline:{

            }break;
            // Handle the camera action
            case R.id.nav_travels:{

            }break;
            case R.id.nav_chats:{

            }break;
            case R.id.nav_settings:{
                i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }break;
            case R.id.nav_signout:{
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                Intent i = new Intent(getApplicationContext(), LoginSplash.class);
                                startActivity(i);
                                finish();
                            }
                        });
            }break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
