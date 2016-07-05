package at.fhooe.mc.android.travel.travellist;

import android.os.AsyncTask;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import at.fhooe.mc.android.main_menu.MainMenu;
import at.fhooe.mc.android.travel.travelmenu.MyTravelsMenu;

/**
 * This class deletes a certain Travel in the Database, which is set, when this class is created.
 */
public class DeleteTravel {

    TravelListItem toDelete;
    public DatabaseReference myRef, trainRef, travelRef;
    String userID;


    public DeleteTravel(TravelListItem item){
        toDelete = item;
        userID = MyTravelsMenu.userID;

        new DeleteAsyn().execute();
    }


    /**
     * Deletes a certain Travel - Entry, which is determined with the creation of the class DeleteTravel,
     * in the Database in the background.
     */
    private class DeleteAsyn extends AsyncTask<Void, Void, Void>{

        public DeleteAsyn(){
            myRef =     MainMenu.database.getDatabase().getReference("Users").child(userID).child("Travels");
            trainRef =  MainMenu.database.getDatabase().getReference("RailjetTraveler").child(toDelete.getDate()).child(String.valueOf(toDelete.getTrainNumber()));
            travelRef = MainMenu.database.getDatabase().getReference("Travels");
            myRef.keepSynced(true);
        }

        @Override
        protected Void doInBackground(Void... params) {

            trainRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(dataSnapshot.getValue().equals(userID)){
                        trainRef.child(dataSnapshot.getKey()).removeValue();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            travelRef.child(toDelete.getID()).removeValue();
            myRef.child(toDelete.getID()).removeValue();

            return null;
        }

    }





}
