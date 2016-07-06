package at.fhooe.mc.android.login;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.main_menu.MainMenu;

public class LoginSplash extends Activity {

    private int RC_SIGN_IN = 69;
    private static FirebaseAuth mInstance;
    private final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);

        mInstance = FirebaseAuth.getInstance();
        Log.i(TAG, "got auth Object");

        if (mInstance.getCurrentUser() != null) {
            //User is found, launch MainMenu
            Log.i(TAG, "User != null, launching main menu");

            Intent i = new Intent(this, MainMenu.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(i);
            finish();
        } else if (mInstance.getCurrentUser() == null) {
            //No User is logged in, start FirebaseUI Login Activity
            Log.i(TAG, "User == null, launching Firebase UI");

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.FACEBOOK_PROVIDER
                            )
                            .setTheme(R.style.CustomFirebaseUITheme)
                            .setLogo(R.drawable.logo2_cropped)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(LoginSplash.this, "User Logid In", Toast.LENGTH_SHORT).show();

                // user is signed in!
                Intent i = new Intent(this, MainMenu.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                Log.i(TAG, "Login complete onActivityResult()");


            } else {
                //Sign in failed, cancelled
                Toast.makeText(LoginSplash.this, "Not Logged In", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Login cancelled");
            }
        }
    }
}
