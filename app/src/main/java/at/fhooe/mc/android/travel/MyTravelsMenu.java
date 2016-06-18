package at.fhooe.mc.android.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Iterator;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.main_menu.NavigationDrawerFragment;
import at.fhooe.mc.android.main_menu.Railchat_Main_Menu;

public class MyTravelsMenu extends AppCompatActivity {

    private static final String TAG = "Railchat:myTravels";
    public static DatabaseReference myRef;
    public String userID = "8c03c4dd-17b1-42aa-af94-e7846cb5049c";
    public static boolean deleteAction;
    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_travels_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.myTravels_FloatingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Railchat_New_Travel.class);
                startActivity(i);
            }
        });


        myRef = Railchat_Main_Menu.database.getDatabase().getReference("User");
        myRef.child(userID);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.myTravels_frameLayout, new MyTravelsMenu_ListFragment());
        fragmentTransaction.commit();


    }


}
