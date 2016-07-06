package at.fhooe.mc.android.main_menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.board.Board;
import at.fhooe.mc.android.database.GetTravels;
import at.fhooe.mc.android.database.InitializeDatabase;
import at.fhooe.mc.android.chat.ChatActivity;
import at.fhooe.mc.android.login.LoginSplash;
import at.fhooe.mc.android.settings.SettingsActivity;
import at.fhooe.mc.android.travel.travelmenu.MyTravelsMenu;

/**
 * This class is the center of everything. It provides the
 * Navigation Drawer to Navigate to other features of the app.
 */
public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static FirebaseAuth mInstance;
    public static InitializeDatabase database = new InitializeDatabase();
    public static GetTravels travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        travel = new GetTravels(this);

        mInstance = FirebaseAuth.getInstance();
        Log.i("LOGIN", "onCreate(): got auth Object");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

            //insert user Data to Drawer Header
            View drawerTop = navigationView.getHeaderView(0);
            TextView userName = (TextView) drawerTop.findViewById(R.id.nav_header_username);
            TextView userGreeting = (TextView) drawerTop.findViewById(R.id.nav_header_greeting);
            final ImageView userPhoto = (ImageView) drawerTop.findViewById(R.id.nav_header_userphoto);

            FirebaseUser user = mInstance.getCurrentUser();
            if (user != null) {
                userName.setText(user.getDisplayName());
                userGreeting.setText("Hello");
                if (user.getPhotoUrl() != null) {
                    Glide.with(this).load(user.getPhotoUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(userPhoto) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MainMenu.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            userPhoto.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                }

                //Upload User data to separate Database-Object to allow communication
                database.getDatabase().getReference("Users").child(user.getUid()).child("Name").setValue(user.getDisplayName());
                database.getDatabase().getReference("Users").child(user.getUid()).child("Photo").setValue(user.getPhotoUrl());

            }
          


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method for handling selected drawer item and launching respective Activity
     * @param item Selected Drawer Item user clicked on
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        final Intent i;


        switch (item.getItemId()) {

            case R.id.nav_board:{
                i = new Intent(getApplicationContext(), Board.class);
                startActivity(i);
            }break;
            case R.id.nav_travels:{
                i = new Intent(getApplicationContext(), MyTravelsMenu.class);
                startActivity(i);
            }
            break;
            case R.id.nav_chats: {
                i = new Intent(MainMenu.this, ChatActivity.class);
                startActivity(i);
            }
            break;
            case R.id.nav_settings: {
                i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
            break;
            case R.id.nav_signout: {
                i = new Intent(MainMenu.this, LoginSplash.class);
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out, start Login again
                                Toast.makeText(MainMenu.this, "You're now signed out", Toast.LENGTH_SHORT).show();
                                startActivity(i);
                                finish();
                            }
                        });
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
