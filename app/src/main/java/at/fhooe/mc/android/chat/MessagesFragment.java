package at.fhooe.mc.android.chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.adapter.FirebaseRecyclerAdapter;
import at.fhooe.mc.android.models.ChatItemModel;
import at.fhooe.mc.android.models.FirebaseArray;
import at.fhooe.mc.android.models.FriendItemHolder;
import at.fhooe.mc.android.models.FriendItemModel;
import at.fhooe.mc.android.models.MessageHolder;
import at.fhooe.mc.android.models.MessageModel;


public class MessagesFragment extends Fragment {
    public static final String TAG = "MessageFragment";
    private FirebaseAuth mAuth;
    private String name;
    private Button mSendButton;
    private EditText mEditText;
    private DatabaseReference mRef;
    private FirebaseUser myUser;
    private DatabaseReference mMessageRef;
    private Query mUserQuery;
    private Query mMembersQuery;
    private Query mMessageQuery;
    private RecyclerView mMessages;
    private LinearLayoutManager mManager;
    private FirebaseArray mMembersArray;
    RelativeLayout myLayout;
    private FirebaseRecyclerAdapter<MessageModel, MessageHolder> mRecyclerViewAdapter;
    String chatId;
    String title;


    public MessagesFragment() {
        // Required empty public constructor
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initObjects(view);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Messages"); //// TODO: 06.07.2016 setName of Chatpartner/Chatname
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditText.getText().length()>0){
                    addNewMessage(mEditText.getText().toString());
                    mEditText.setText("");
                    mEditText.clearAnimation();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(myLayout.getWindowToken(), 0);
                }

            }
        });
    }
    private void initObjects(View view){
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        myUser = mAuth.getCurrentUser();
        Bundle args = getArguments();
        if(args.getBoolean("newChat")){
            //if(!chatAlreadyExists(args.getString("uid"))){
                addNewChat(args.getString("uid"),args.getString("title"));
            title = args.getString("title");
            //}
        }else {
            chatId = args.getString("chatId");
            title = args.getString("title");
        }
        mMembersQuery = mRef.child("Members").child(chatId);
        mMembersArray = new FirebaseArray(mMembersQuery);
        mMessageRef = mRef.child("Messages");
        mMessageQuery = mRef.child("Messages").child(chatId);
        mMessages = (RecyclerView) view.findViewById(R.id.messagesList);
        myLayout = (RelativeLayout) view.findViewById(R.id.message_fragment);
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(false);
        mMessages.setHasFixedSize(false);
        mMessages.setLayoutManager(mManager);
        mEditText = (EditText) view.findViewById(R.id.messageEdit);
        mSendButton = (Button) view.findViewById(R.id.sendButton);
    }

    /**
     * Adds new chats in database for both users
     * @param uid
     * @param name
     */
    private void addNewChat(String uid, String name){
        ChatItemModel chatItemModel = new ChatItemModel(name,"New Chat");
        chatId = mRef.child("Chats").child(myUser.getUid()).push().getKey();
        chatItemModel.setTimestamp();
        mRef.child("Chats").child(myUser.getUid()).child(chatId).setValue(chatItemModel);
        chatItemModel.setTitle(myUser.getDisplayName());
        chatItemModel.setTimestamp();
        Log.d(TAG,"MyUserUid: "+ myUser.getUid());
        Log.d(TAG,"Other User Uid: "+uid);
        mRef.child("Chats").child(uid).child(chatId).setValue(chatItemModel);
        FriendItemModel friendItemModel = new FriendItemModel(name,uid);
        mRef.child("Members").child(chatId).push().setValue(friendItemModel);
        friendItemModel.setName(myUser.getDisplayName());
        friendItemModel.setUid(myUser.getUid());
        mRef.child("Members").child(chatId).push().setValue(friendItemModel);
    }

    /**
     * Adds a new message to the database
     * @param message
     */
    private void addNewMessage(String message){
        MessageModel newMessage = new MessageModel();
        newMessage.setMessage(message);
        newMessage.setName(myUser.getDisplayName());
        mMessageRef.child(chatId).push().setValue(newMessage);
        updateChat(message);

    }

    /**
     * updates the chat database with the new lastMessage and a new timestamp
     * @param message
     */
    private void updateChat(String message){
        ArrayList <FriendItemModel> members = getMembers();
        ChatItemModel myChatItem = new ChatItemModel();
        myChatItem.setLastMessage(message);
        myChatItem.setTimestamp();
        for(int i = 0; i< members.size(); i++){
            if(members.get(i).getUid().equals(myUser.getUid())){
                myChatItem.setTitle(title);
                mRef.child("Chats").child(members.get(i).getUid()).child(chatId).setValue(myChatItem);
            }else{
                myChatItem.setTitle(myUser.getDisplayName());
                mRef.child("Chats").child(members.get(i).getUid()).child(chatId).setValue(myChatItem);
            }
        }
    }

    /**
     * returns all members of the current chat
     * @return
     */
    private ArrayList<FriendItemModel> getMembers(){
        ArrayList<FriendItemModel> members = new ArrayList<>();
        for(int i = 0; i < mMembersArray.getCount(); i++){
            members.add(mMembersArray.getItem(i).getValue(FriendItemModel.class));
        }
        return members;
    }
    private ArrayList<ChatItemModel> getChats(Query chatQuery){
        FirebaseArray mFirebaseArray = new FirebaseArray(chatQuery);
        ArrayList<ChatItemModel> chats = new ArrayList<>();
        for(int i = 0; i < mFirebaseArray.getCount(); i++){
            chats.add(mFirebaseArray.getItem(i).getValue(ChatItemModel.class));
        }
        return chats;
    }
    private boolean chatAlreadyExists(String uid){
        Query chatQuery = mRef.child("Chats").child(myUser.getUid());
        ArrayList<ChatItemModel> chats = getChats(chatQuery);
        for(int i = 0; i<chats.size(); i++){
            if(chats.get(i).getTitle().equals(uid)){
                return true;
            }
        }
        return false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }


    /**
     * Attaches the RecyclerViewAdapter to display the messages
     */
    private void attachRecyclerViewAdapter(){
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<MessageModel, MessageHolder>(
                MessageModel.class, R.layout.message_item, MessageHolder.class, mMessageQuery) {
            @Override
            protected void populateViewHolder(MessageHolder viewHolder, MessageModel model, int position) {
                if(myUser.getDisplayName().equals(model.getName())){
                    viewHolder.setIsSender(true);
                }
                else{
                    viewHolder.setIsSender(false);
                }
                viewHolder.setName(model.getName());
                viewHolder.setMessage(model.getMessage());
            }
        };
        mRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mManager.smoothScrollToPosition(mMessages, null, mRecyclerViewAdapter.getItemCount());
            }
        });

        mMessages.setAdapter(mRecyclerViewAdapter);

    }
    public boolean isSignedIn() {
        return (mAuth.getCurrentUser() != null);
    }
}
