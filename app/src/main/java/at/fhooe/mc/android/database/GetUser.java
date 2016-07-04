package at.fhooe.mc.android.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class gets the current user and has a method, which returns
 * the userID.
 */
public class GetUser {

    private FirebaseUser user;

    public GetUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public String getUserID(){
        return user.getUid();
    }


}
