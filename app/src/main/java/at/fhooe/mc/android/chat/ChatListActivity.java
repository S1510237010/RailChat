package at.fhooe.mc.android.chat;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
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

public class ChatListActivity extends AppCompatActivity {
    public static final String TAG = "ChatListActivity";

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser myUser;
    private DatabaseReference mChatsRef;
    private Button newChatButton;
    private Query mChatRef;
    private RecyclerView mChats;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<ChatItemModel, ChatItemHolder> mRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        myUser = mAuth.getCurrentUser();
        mChatsRef = mRef.child("Chats").child(myUser.getUid());
        mChatRef = mRef.child("Chats").child(myUser.getUid()).limitToLast(50);
        mChats = (RecyclerView) findViewById(R.id.chatsRecyclerView);
        newChatButton = (Button)findViewById(R.id.new_chat);
        newChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newChat();
            }
        });


        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(false);

        mChats.setHasFixedSize(false);
        mChats.setLayoutManager(mManager);
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
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<ChatItemModel, ChatItemHolder>(
                ChatItemModel.class, R.layout.chat_item, ChatItemHolder.class, mChatRef) {
            @Override
            protected void populateViewHolder(ChatItemHolder viewHolder, ChatItemModel model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setLastMessage(model.getLastMessage());

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
    private void newChat(){


        ChatItemModel ch = new ChatItemModel("Martin", "Blablabla");

        mChatsRef.push().setValue(ch);

    }




}
