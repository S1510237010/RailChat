package at.fhooe.mc.android.chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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
    private Button mSendButton;
    private EditText mEditText;
    private DatabaseReference mRef;
    private FirebaseUser myUser;
    private DatabaseReference mMemberRef;
    private DatabaseReference mMessageRef;
    private DatabaseReference mChatRef;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




       // Toast.makeText(getActivity(), chatId, Toast.LENGTH_LONG ).show();
        //Toast.makeText(getActivity(), title, Toast.LENGTH_LONG ).show();







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
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditText.getText().length()>0){
                    addNewMessage(mEditText.getText().toString(),myUser.getUid());
                    mEditText.setText("");
                    mEditText.clearAnimation();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(myLayout.getWindowToken(), 0);
                    notifyMembers();


                }

            }
        });
    }
    private void initObjects(View view){
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        myUser = mAuth.getCurrentUser();
        //mChatsRef = mRef.child("Chats").child(myUser.getUid());

        Bundle args = getArguments();
        if(args.getBoolean("newChat")){
            addNewChat(args.getString("uid"),args.getString("title"));

        }else{
            chatId = args.getString("chatId");
            title = args.getString("title");
        }
        Log.d(TAG,chatId);
        mMembersQuery = mRef.child("Members").child(chatId);


        mMembersArray = new FirebaseArray(mMembersQuery);

        //mMemberRef = mRef.child("Members");
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
    public void notifyMembers(){
        //mMembersArray = new FirebaseArray(mMembersQuery);

    }

    private void addNewChat(String uid, String name){
        ChatItemModel chatItemModel = new ChatItemModel(name,"New Chat");
        chatId = mRef.child("Chats").child(myUser.getUid()).push().getKey();
        chatItemModel.setTimestamp();
        mRef.child("Chats").child(myUser.getUid()).child(chatId).setValue(chatItemModel);
        chatItemModel.setTitle(myUser.getUid());
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
    private void addNewMessage(String message, String uid){
        MessageModel newMessage = new MessageModel();
        newMessage.setMessage(message);
        newMessage.setName(uid);
        mMessageRef.child(chatId).push().setValue(newMessage);
        updateChat(message, uid);

    }
    private void updateChat(String message, String uid){
        ArrayList <FriendItemModel> members = getMembers();
        ChatItemModel myChatItem = new ChatItemModel();
        myChatItem.setLastMessage(message);
        myChatItem.setTitle(uid);
        myChatItem.setTimestamp();

        for(int i = 0; i < members.size(); i++){
            mRef.child("Chats").child(members.get(i).getUid()).child(chatId).setValue(myChatItem);
        }


    }

    private ArrayList<FriendItemModel> getMembers(){
        ArrayList<FriendItemModel> members = new ArrayList<>();
        for(int i = 0; i < mMembersArray.getCount(); i++){
            members.add(mMembersArray.getItem(i).getValue(FriendItemModel.class));
        }
        return members;

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    private void attachRecyclerViewAdapter(){
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<MessageModel, MessageHolder>(
                MessageModel.class, R.layout.message_item, MessageHolder.class, mMessageQuery) {
            @Override
            protected void populateViewHolder(MessageHolder viewHolder, MessageModel model, int position) {
                //if(myUser.getUid().equals(model.getName())){

                    viewHolder.setName(model.getName());
                    viewHolder.setMessage(model.getMessage());

                //}



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
