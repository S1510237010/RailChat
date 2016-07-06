package at.fhooe.mc.android.chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.adapter.FirebaseRecyclerAdapter;
import at.fhooe.mc.android.adapter.NewFriendsAdapter;
import at.fhooe.mc.android.models.FirebaseArray;
import at.fhooe.mc.android.models.FriendItemHolder;
import at.fhooe.mc.android.models.FriendItemModel;


public class AddNewFriendFragment extends Fragment {
    private EditText searchStr;
    public static final String TAG = "AddNewFriendFragment";
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser myUser;
    private Query mQuery;
    private Query mQuerySearchList;
    private ListView mUsers;
    private LinearLayoutManager mManager;
    private NewFriendsAdapter adapter;

    public AddNewFriendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        myUser = mAuth.getCurrentUser();
        mQuery = mRef.child("Users");
        mQuerySearchList = mRef.child("Friends").child(myUser.getUid());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_friend, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mUsers = (ListView) view.findViewById(R.id.new_friends_list);
        adapter = new NewFriendsAdapter(getContext());
        adapter.setActivity(getActivity());
        mUsers.setAdapter(adapter);






        mRef.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getKey();
                String name = dataSnapshot.child("Name").getValue().toString();
                if(!myUser.getUid().equals(id)) {
                    FriendItemModel myFriend = new FriendItemModel(name, id);
                    adapter.add(myFriend);
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
    }
}
