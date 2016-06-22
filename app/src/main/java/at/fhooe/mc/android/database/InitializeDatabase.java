package at.fhooe.mc.android.database;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Anna on 07.06.2016.
 * This class initialize a FirebaseDatabase and sets the persistence to true, so the data will be persistenced
 * for a period of time.
 */
public class InitializeDatabase {

    private FirebaseDatabase database;

    public InitializeDatabase(){
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
    }

    /**
     * Returns the Database for further use in the activities.
     *
     * @return  DatabaseInstance of our Firebase Database
     */
    public FirebaseDatabase getDatabase(){
        return database;
    }


}
