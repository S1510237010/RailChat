package at.fhooe.mc.android.database;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Anna on 07.06.2016.
 */
public class InitializeDatabase {

    private FirebaseDatabase database;

    public InitializeDatabase(){
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
    }

    public FirebaseDatabase getDatabase(){
        return database;
    }


}
