package at.fhooe.mc.android.login;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.main_menu.Railchat_Main_Menu;

public class LoginSplash extends Activity {

    private int RC_SIGN_IN = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.i("Login", "got auth Object");

        if (auth.getCurrentUser() != null) {
            Toast.makeText(LoginSplash.this, "User != null", Toast.LENGTH_SHORT).show();
            Log.i("Login", "User != null, launching main menu");

            Intent i = new Intent(this, Railchat_Main_Menu.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(LoginSplash.this, "User == null", Toast.LENGTH_SHORT).show();
            Log.i("Login", "User == null, launching Firebase UI");

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.FACEBOOK_PROVIDER)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(LoginSplash.this, "User Loggid In", Toast.LENGTH_SHORT).show();

                // user is signed in!
                Intent i = new Intent(this, Railchat_Main_Menu.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                Log.i("Login", "Login complete onActivityResult()");


            } else {
                //Sign in failed, cancelled
                Toast.makeText(LoginSplash.this, "Not Logged In", Toast.LENGTH_SHORT).show();
                Log.i("Login", "Login cancelled");
            }
        }
    }

}
