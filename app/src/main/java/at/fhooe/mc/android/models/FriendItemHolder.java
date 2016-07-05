package at.fhooe.mc.android.models;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.chat.ChatActivity;
import at.fhooe.mc.android.chat.MessagesFragment;

/**
 * Created by Martin on 01.07.2016.
 */
public class FriendItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    ChatActivity myActivity;
    String uid,name;


    public FriendItemHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mView.setOnClickListener(this);
    }

    public void setFriendName(String friendNameStr) {
        TextView friendName = (TextView) mView.findViewById(R.id.friend_text);
        friendName.setText(friendNameStr);
        name = friendNameStr;
    }

    public void setMyActivity(Activity myActivity) {
        this.myActivity = (ChatActivity) myActivity;
    }
    public void setUid(String uid){
        this.uid = uid;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = myActivity.getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        Fragment fragment = new MessagesFragment();
        args.putBoolean("newChat",true);
        args.putString("uid",uid);
        args.putString("title",name);
        fragment.setArguments(args);
        ft.replace(R.id.chat_container, fragment);
        ft.commit();

    }
}
