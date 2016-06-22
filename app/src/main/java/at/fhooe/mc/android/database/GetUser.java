package at.fhooe.mc.android.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Anna on 22.06.2016.
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
