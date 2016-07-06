package at.fhooe.mc.android.models;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.chat.ChatActivity;
import at.fhooe.mc.android.chat.ChatFragment;
import at.fhooe.mc.android.chat.FriendsFragment;
import at.fhooe.mc.android.chat.MessagesFragment;


/**
 * Created by Martin on 24.06.2016.
 */
public class ChatItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    ChatActivity myActivity;
    String chatId, title;


    public ChatItemHolder(View itemView){
        super(itemView);
        mView = itemView;
        mView.setOnClickListener(this);
    }
    public void setTitle(String title){
        this.title = title;
        TextView titleField = (TextView) mView.findViewById(R.id.title_name);
        titleField.setText(title);
    }
    public void setLastMessage(String lastMessage){
        TextView lastMessageField = (TextView) mView.findViewById(R.id.last_message);
        lastMessageField.setText(lastMessage);
    }
    public void setMyActivity(Activity myActivity){
        this.myActivity = (ChatActivity)myActivity;
    }
    public void setChatId(String id){
        this.chatId=id;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = myActivity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = new MessagesFragment();
        ft.addToBackStack(ChatFragment.TAG);
        Bundle args = new Bundle();
        args.putBoolean("newChat",false);
        args.putString("chatId",chatId);
        args.putString("title",title);
        fragment.setArguments(args);
        ft.replace(R.id.chat_container,fragment);
        ft.commit();

    }
}
