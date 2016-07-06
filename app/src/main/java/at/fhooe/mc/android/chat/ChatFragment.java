package at.fhooe.mc.android.chat;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.adapter.FirebaseRecyclerAdapter;
import at.fhooe.mc.android.models.ChatItemHolder;
import at.fhooe.mc.android.models.ChatItemModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    public static final String TAG = "ChatFragment";
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private DatabaseReference mMessageRef;
    private DatabaseReference mMemberRef;
    private FirebaseUser myUser;
    private DatabaseReference mChatsRef;
    private Query mChatRef;
    private RecyclerView mChats;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<ChatItemModel, ChatItemHolder> mRecyclerViewAdapter;


    public ChatFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        myUser = mAuth.getCurrentUser();

        mChatsRef = mRef.child("Chats").child(myUser.getUid());
        mChatRef = mRef.child("Chats").child(myUser.getUid()).limitToLast(50);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        FloatingActionButton newChat = (FloatingActionButton) view.findViewById(R.id.new_chat);
        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new FriendsFragment();
                ft.replace(R.id.chat_container,fragment);
                ft.commit();
            }
        });

        mChats = (RecyclerView) getView().findViewById(R.id.chatsRecyclerView);



        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(false);

        mChats.setHasFixedSize(false);
        mChats.setLayoutManager(mManager);


    }
    @Override
    public void onStart(){
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Chats");


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
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<ChatItemModel, ChatItemHolder>(
                ChatItemModel.class, R.layout.chat_item, ChatItemHolder.class, mChatRef) {
            @Override
            protected void populateViewHolder(ChatItemHolder viewHolder, ChatItemModel model, int position) {
                viewHolder.setMyActivity(getActivity());
                viewHolder.setTitle(model.getTitle());
                viewHolder.setLastMessage(model.getLastMessage());
                viewHolder.setChatId(mRecyclerViewAdapter.getKeyForPos(position));

            }
        };
        mRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mManager.smoothScrollToPosition(mChats, null, mRecyclerViewAdapter.getItemCount());
            }
        });

        mChats.setAdapter(mRecyclerViewAdapter);

    }



    public boolean isSignedIn() {
        return (mAuth.getCurrentUser() != null);
    }


}
