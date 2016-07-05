package at.fhooe.mc.android.board;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.database.GetTravels;
import at.fhooe.mc.android.main_menu.MainMenu;

public class Board extends AppCompatActivity implements View.OnClickListener {

    protected DatabaseReference myRef_Board;
    protected BoardAdapter adapter;
    protected ListView listView;
    protected GetTravels travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

//        BoardAdapter adapter = new BoardAdapter(this);
//        adapter.add(new BoardData("ÖBB is voll super",null,"Basti"));
//        setListAdapter(adapter);


        adapter = new BoardAdapter(this);
        myRef_Board = MainMenu.database.getDatabase().getReference();
        buildBoard();

        listView = (ListView)findViewById(R.id.board_list);
        if (listView != null) listView.setAdapter(adapter);
        else Toast.makeText(this,"Adapter ist null",Toast.LENGTH_SHORT).show();

        Button b;
        b = (Button)findViewById(R.id.board_button_add);
        b.setOnClickListener(this);

    }

    private void buildBoard() {
        FirebaseUser user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
        final String userID = user.getUid();
        final boolean[] inTrain = {false};
        final String[] rjID = new String[]{""};

        final Calendar[] date = {Calendar.getInstance()};
        int day = date[0].get(Calendar.DAY_OF_MONTH);
        int month = date[0].get(Calendar.MONTH);
        int year = date[0].get(Calendar.YEAR);
        String s = day + "-" + (month + 1) + "-" + year;


        travel = new GetTravels(this);
        int rj  = travel.travelToday();
        if (rj == 0) Toast.makeText(Board.this, "You are not logged in to a Railchat yet!", Toast.LENGTH_SHORT).show();
        else {
            myRef_Board.child("Boards").child(String.valueOf(rj)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //Gets called at beginning and at a change
                    //System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");

                    if (snapshot.getChildrenCount() > 0) {
                        //Clear the view
                        adapter.clear();
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            BoardData data = postSnapshot.getValue(BoardData.class);
                            adapter.add(data);
                        }
                    }else {
                        Toast.makeText(Board.this,"No posts on board! Post something yourself!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


//        final DatabaseReference ref_RJ = myRef_Board.child("RailjetTraveler").child(s);
//        ref_RJ.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.exists()) Toast.makeText(Board.this,"You are not logged in to a travel so far! Please log in to a Railjet!",Toast.LENGTH_SHORT).show();
//                else {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
////                        BoardData data = postSnapshot.getValue(BoardData.class);
////                        adapter.add(data);
//                        String train = postSnapshot.getKey();
//                        DatabaseReference ref_Users = ref_RJ.child(train);
//                        ref_Users.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if (!dataSnapshot.exists())
//                                    Toast.makeText(Board.this, "No users in Railchat found!", Toast.LENGTH_SHORT).show();
//                                else {
//                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                                        if (userID.equals(postSnapshot.getValue())) {
//                                            inTrain[0] = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                System.out.println(databaseError.getMessage());
//                            }
//                        });
//                        if (inTrain[0]) {
//                            rjID[0] = train;
//                            break;
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println(databaseError.getMessage());
//            }
//        });
//
//        if (inTrain[0]) {
//            myRef_Board.child("Boards").child(rjID[0]).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot snapshot) {
//
//                    //Gets called at beginning and at a change
//                    //System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
//
//                    if (snapshot.getChildrenCount() > 0) {
//                        //Clear the view
//                        adapter.clear();
//                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                            BoardData data = postSnapshot.getValue(BoardData.class);
//                            adapter.add(data);
//                        }
//                    }else {
//                        Toast.makeText(Board.this,"No posts on board! Post something yourself!",Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    System.out.println("The read failed: " + databaseError.getMessage());
//                }
//            });
//        }

    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()){
            case R.id.board_button_add : {
                Intent i = new Intent(this,NewItem.class);
                startActivity(i);
            }break;
            default : {
                Toast.makeText(this,"ClickListener no id found",Toast.LENGTH_SHORT).show();
            } break;
        }
    }
}
