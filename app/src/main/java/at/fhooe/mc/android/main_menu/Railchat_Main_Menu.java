package at.fhooe.mc.android.main_menu;

import android.app.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.InitializeDatabase;

public class Railchat_Main_Menu extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    public String user = "anna.hausi@gmx.at";
    public String psw = "1234";
    public String userID = "8c03c4dd-17b1-42aa-af94-e7846cb5049c";
    public static InitializeDatabase database = new InitializeDatabase();


    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railchat_main_menue);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, new TrainLogIn())
                .commit();
        mTitle = getTitle();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.myTravels);
                break;
            case 2:
                mTitle = getString(R.string.settings);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

}