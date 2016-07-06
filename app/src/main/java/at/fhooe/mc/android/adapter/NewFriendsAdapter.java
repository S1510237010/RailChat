package at.fhooe.mc.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.chat.ChatActivity;
import at.fhooe.mc.android.chat.ChatFragment;
import at.fhooe.mc.android.chat.FriendsFragment;
import at.fhooe.mc.android.chat.MessagesFragment;
import at.fhooe.mc.android.models.FriendItemModel;

/**
 * Created by Martin on 06.07.2016.
 */
public class NewFriendsAdapter extends ArrayAdapter<FriendItemModel>{
    Context context;
    ChatActivity myActivity;
    public NewFriendsAdapter(Context context) {
        super(context, R.layout.friend_item);
        this.context = context;
    }
    public void setActivity(Activity activity){
        myActivity = (ChatActivity) activity;
    }

    @Override
    public View getView(int pos, View v, ViewGroup p) {
        //return super.getView(position, convertView, parent);
        Log.e("Friends Adapter","Try this");
        if (v == null) {
            Context c = getContext();
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.friend_item,p,false);
        }
        final FriendItemModel friend = getItem(pos);
        if(friend != null){
            TextView tx = (TextView) v.findViewById(R.id.friend_text);
            tx.setText(friend.getName());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addUserToFriendsList(friend);
                }
            });

        }
        return v;
    }

    public void addUserToFriendsList(FriendItemModel friend){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser myUser = FirebaseAuth.getInstance().getCurrentUser();

        Log.d("Adapter", "Added User to Friends");
        mRef.child("Friends").child(myUser.getUid()).push().setValue(friend);
        mRef.child("Friends").child(friend.getUid()).push().setValue(new FriendItemModel(myUser.getDisplayName(),myUser.getUid()));

        FragmentTransaction ft = myActivity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = new FriendsFragment();
        ft.replace(R.id.chat_container,fragment);
        ft.commit();
    }
}
