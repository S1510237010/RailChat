package at.fhooe.mc.android.travel;

import android.os.AsyncTask;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import at.fhooe.mc.android.main_menu.Railchat_Main_Menu;

/**
 * Created by Anna on 19.06.2016.
 */
public class DeleteTravel {

    TravelListItem toDelete;
    public DatabaseReference myRef, trainRef;
    String userID;


    public DeleteTravel(TravelListItem item){
        toDelete = item;
        myRef = Railchat_Main_Menu.database.getDatabase().getReference("Stations");
        userID = "8c03c4dd-17b1-42aa-af94-e7846cb5049c";

        new DeleteAsyn().execute();
    }


    private class DeleteAsyn extends AsyncTask<Void, Void, Void>{

        public DeleteAsyn(){
            myRef = Railchat_Main_Menu.database.getDatabase().getReference("User").child(userID);
            trainRef = Railchat_Main_Menu.database.getDatabase().getReference("RailJets").child(String.valueOf(toDelete.getTrainNumber())).child("Traveler").child(toDelete.getDate());
            myRef.keepSynced(true);
        }

        @Override
        protected Void doInBackground(Void... params) {


            trainRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot.getValue().toString().equals(userID)){
                        trainRef.child(dataSnapshot.getKey()).removeValue();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });


            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot.child("Train").getValue().toString().equals(String.valueOf(toDelete.getTrainNumber()))){
                        System.out.println("Same Train");
                        if (dataSnapshot.child("Date").getValue().toString().equals(toDelete.getDate())){
                            System.out.println("Same Date");
                            if (dataSnapshot.child("To").getValue().toString().equals(toDelete.getTo())){
                                if (dataSnapshot.child("From").getValue().toString().equals(toDelete.getFrom())){
                                    myRef.child(dataSnapshot.getKey()).removeValue();
                                    System.out.println("DELETED!!");
                                }
                            }
                        }
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            return null;
        }
    }





}
