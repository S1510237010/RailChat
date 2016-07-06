package at.fhooe.mc.android.chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.adapter.FirebaseRecyclerAdapter;
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
    private RecyclerView mUsers;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<FriendItemModel, FriendItemHolder> mRecyclerViewAdapter;

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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mUsers = (RecyclerView) getView().findViewById(R.id.new_Friends);



        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(false);

        mUsers.setHasFixedSize(false);
        mUsers.setLayoutManager(mManager);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_friend, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        searchStr = (EditText) view.findViewById(R.id.search_friend);
        Button search = (Button) view.findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchStr.getText().toString().trim().length() >= 3) {
                    if(mRecyclerViewAdapter != null){
                        mRecyclerViewAdapter.cleanup();
                    }
                    mQuerySearchList = mRef.child("Users") ;
                    //equalTo(searchStr.getText().toString());

                }
            }
        });
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

    private void attachRecyclerViewAdapter(){
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<FriendItemModel, FriendItemHolder>(
                FriendItemModel.class, R.layout.chat_item, FriendItemHolder.class, mQuerySearchList) {
            @Override
            protected void populateViewHolder(FriendItemHolder viewHolder, FriendItemModel model, int position) {
                viewHolder.setFriendName(model.getName());

            }
        };
        mRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mManager.smoothScrollToPosition(mUsers, null, mRecyclerViewAdapter.getItemCount());
            }
        });

        mUsers.setAdapter(mRecyclerViewAdapter);

    }


    public boolean isSignedIn() {
        return (mAuth.getCurrentUser() != null);
    }


}
