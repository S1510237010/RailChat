package at.fhooe.mc.android.board;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

/**
 * This class is the MainClass for the Board, which is called, when the option
 * "Timeline" is clicked in the NavigationDrawer of the MainMenu.
 * It creates the board for the train that the user has selected in his travel.
 * If there is no travel on that date for that user, there will not be a board.
 * If there are no posts on the board, the user will be told that.
 *
 * The items will be created in a listView with an Adapter.
 */
public class Board extends AppCompatActivity implements View.OnClickListener {

    protected DatabaseReference myRef_Board;
    protected BoardAdapter adapter;
    protected ListView listView;
    protected static int rjID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        adapter = new BoardAdapter(this);
        myRef_Board = MainMenu.database.getDatabase().getReference();
        buildBoard();

        listView = (ListView)findViewById(R.id.board_list);
        if (listView != null) listView.setAdapter(adapter);
        else Toast.makeText(this,"Adapter ist null",Toast.LENGTH_SHORT).show();

        TextView view = (TextView)findViewById(R.id.board_list_title);
        if (rjID != 0) {
            setTitle("Board - Railjet " + rjID);
            view.setText("Welcome to the board of RJ " + rjID + "!");
        }
        else {
            view.setText("Log in to a RJ now!");
        }

        Button b;
        b = (Button)findViewById(R.id.board_button_new);
        if (rjID != 0) b.setOnClickListener(this);
        else b.setVisibility(View.INVISIBLE);

    }

    /**
     * buildBoard will get the items from the Database.
     * It also checks first, if the user has a travel on that day before.
     * If he has, it will call the database and put the items in the adpater, where they will be built.
     * If there are no posts on the board, it will tell that the user.
     */
    private void buildBoard() {

        final Calendar[] date = {Calendar.getInstance()};
        int day = date[0].get(Calendar.DAY_OF_MONTH);
        int month = date[0].get(Calendar.MONTH);
        int year = date[0].get(Calendar.YEAR);
        String s = day + "-" + (month + 1) + "-" + year;


        int rj  = MainMenu.travel.travelToday();
        if (rj == 0) Toast.makeText(Board.this, "You are not logged in to a Railchat yet!", Toast.LENGTH_SHORT).show();
        else {
            rjID = rj;
            myRef_Board.child("Boards").child(String.valueOf(rj)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //Gets called at beginning and at a change
                    //System.out.println("There are " + snapshot.getChildrenCount() + "  posts");
                    if (snapshot.getChildrenCount() > 0) {
                        //Clear the view first, so items wont be on twice
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
                    System.out.println(databaseError.getMessage());
                }
            });
        }
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()){
            case R.id.board_button_new : {
                Intent i = new Intent(this,NewItem.class);
                startActivity(i);
            }break;
            default : {
                Toast.makeText(this,"ClickListener no id found",Toast.LENGTH_SHORT).show();
            } break;
        }
    }
}
