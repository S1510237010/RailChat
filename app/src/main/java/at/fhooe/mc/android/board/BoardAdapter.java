package at.fhooe.mc.android.board;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.main_menu.MainMenu;

/**
 * BoardAdapter helps creating the posts for the listView in the Board class.
 * It also has Button OnClickListener, if the user is voting for a post. It will update the Database and show the changes to the users.
 */
class BoardAdapter extends ArrayAdapter<BoardData> {
    protected boolean userVoted;

    public BoardAdapter(Context context) {super(context, R.layout.activity_board_item);}

    @Override
    public View getView(int _pos, View _v, ViewGroup _p) {
        //return super.getView(position, convertView, parent);
        if (_v == null) {
            Context c = getContext();
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            _v = inflater.inflate(R.layout.activity_board_item,null);
            _v = inflater.inflate(R.layout.activity_board_item,_p,false);
        }
        final BoardData data = getItem(_pos);

        if (data != null) {
            TextView tv = null;
            tv = (TextView)_v.findViewById(R.id.board_title);
            tv.setText(data.getTitle());
            tv = (TextView)_v.findViewById(R.id.board_info);
            StringBuffer buffer = new StringBuffer();
            buffer.append("submitted by " + data.getUser() + " on " + data.getHours() + ":");
            if (data.getMinutes() < 10) buffer.append("0");
            buffer.append(data.getMinutes());
            tv.setText(buffer.toString());

            tv = (TextView)_v.findViewById(R.id.board_link);
            if (data.getLink() != null) tv.setText(data.getLink());
            else tv.setVisibility(View.INVISIBLE);

            tv = (TextView)_v.findViewById(R.id.board_number);
            tv.setText(String.valueOf(data.upvote-data.downvote));


            final int rjID = Board.rjID;

            final boolean userVoting = userHasVoted(rjID, data);
//            Log.i("Voting", "" + userVoting);

            final Button b = (Button)_v.findViewById(R.id.board_upvote);
            if (userVoting) {
                final DatabaseReference ref = MainMenu.database.getDatabase().getReference().child("Boards").child(String.valueOf(rjID)).child(data.getID()).child("users");
                if (ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey().equals(1))
                b.setTextColor(Color.BLUE);
            }
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DatabaseReference ref = MainMenu.database.getDatabase().getReference().child("Boards").child(String.valueOf(rjID)).child(data.getID());
                    final DatabaseReference refUsers = ref.child("users");

                    if (!userVoting) {
                        Map<String,Object> newUpvote = new HashMap<String,Object>();
                        newUpvote.put("upvote",data.getUpvote()+1);

                        ref.updateChildren(newUpvote);
                        b.setTextColor(Color.BLUE);

                        Map<String,Object> newUser = new HashMap<String, Object>();
                        newUser.put(FirebaseAuth.getInstance().getCurrentUser().getUid(),true);
                        refUsers.updateChildren(newUser);
                    }else {
                        Toast.makeText(getContext(), "You already voted for this post!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final Button c  = (Button)_v.findViewById(R.id.board_downvote);
//            if (userVoting[0] && !userVoting[1]) c.setTextColor(Color.BLUE);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final DatabaseReference ref = MainMenu.database.getDatabase().getReference().child("Boards").child(String.valueOf(rjID)).child(data.getID());
                    final DatabaseReference refUsers = ref.child("users");

                    if (!userVoting) {
                        Map<String,Object> newUpvote = new HashMap<String,Object>();
                        newUpvote.put("downvote",data.getDownvote()+1);

                        ref.updateChildren(newUpvote);
                        c.setTextColor(Color.BLUE);

                        Map<String,Object> newUser = new HashMap<String, Object>();
                        newUser.put(FirebaseAuth.getInstance().getCurrentUser().getUid(),false);
                        refUsers.updateChildren(newUser);
                    }else Toast.makeText(getContext(), "You already voted for this post!", Toast.LENGTH_SHORT).show();
                }
            });
        }


        return _v;
    }

    /**
     *
     * @param rjID Railjet-ID to get in the right child in the Database
     * @param data BoardData to get in the right child
     * @return if User has voted on the post or not
     */
    private boolean userHasVoted(int rjID, BoardData data) {
        final DatabaseReference ref = MainMenu.database.getDatabase().getReference().child("Boards").child(String.valueOf(rjID)).child(data.getID());
        final DatabaseReference refUsers = ref.child("users");
        userVoted = false;
        refUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                    hasVoted[0] = false;
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        if (postSnapshot.getKey().equals(userID)) {
                            userVoted = true;
//                            Log.i("Voting","User has voted");
//                            Toast.makeText(getContext(), "You already voted for this post!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return userVoted;

    }
}
