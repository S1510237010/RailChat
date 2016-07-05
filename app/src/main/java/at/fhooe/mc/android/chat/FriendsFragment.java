package at.fhooe.mc.android.chat;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.adapter.FirebaseRecyclerAdapter;
import at.fhooe.mc.android.models.FriendItemHolder;
import at.fhooe.mc.android.models.FriendItemModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {
    public final static String TAG = "FriendsFragment";
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private DatabaseReference mFriendsRef;
    private Query mFriendsQuery;
    private FirebaseUser mUser;
    private RecyclerView mFriends;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<FriendItemModel, FriendItemHolder> mRecyclerViewAdapter;


    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //mFriendsRef = mRef.child("Users");
        //mFriendsQuery = mRef.child("Users");
        mFriendsQuery = mRef.child("Friends").child(mUser.getUid());
        mFriendsRef = mRef.child("Friends");
        mFriends = (RecyclerView) view.findViewById(R.id.friends_list);
        FloatingActionButton newFriend = (FloatingActionButton) view.findViewById(R.id.new_friend);
        newFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendItemModel nFriend = new FriendItemModel();
                nFriend.setName("Martin S.");
                nFriend.setUid("745R6u7FcpNHHpSKKXQVVa7UTqJ2");
                mFriendsRef.child(mUser.getUid()).push().setValue(nFriend);
                Log.d(TAG,mUser.getDisplayName());
                Log.d(TAG,"asfasfasfas");
                nFriend.setName(mUser.getDisplayName());
                nFriend.setUid(mUser.getUid());
                mFriendsRef.child("745R6u7FcpNHHpSKKXQVVa7UTqJ2").push().setValue(nFriend);
            }
        });




        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(false);

        mFriends.setHasFixedSize(false);
        mFriends.setLayoutManager(mManager);
    }

    private void attachRecyclerViewAdapter(){

        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<FriendItemModel, FriendItemHolder>(
                FriendItemModel.class, R.layout.friend_item, FriendItemHolder.class, mFriendsQuery) {
            @Override
            protected void populateViewHolder(FriendItemHolder viewHolder, FriendItemModel model, int position) {
                Toast.makeText(getContext(),model.getName(),Toast.LENGTH_LONG).show();
                viewHolder.setMyActivity(getActivity());
                viewHolder.setFriendName(model.getName());
                viewHolder.setUid(model.getUid());
            }
        };
        mRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mManager.smoothScrollToPosition(mFriends, null, mRecyclerViewAdapter.getItemCount());
            }
        });

        mFriends.setAdapter(mRecyclerViewAdapter);

    }

    @Override
    public void onStart(){
        super.onStart();

        if(!isSignedIn()){
            //TODO: Return to Login
        }else{
            attachRecyclerViewAdapter();
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mRecyclerViewAdapter != null){
            mRecyclerViewAdapter.cleanup();
        }
    }

    public boolean isSignedIn() {
        return (mAuth.getCurrentUser() != null);
    }
}
