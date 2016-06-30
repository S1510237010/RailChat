package at.fhooe.mc.android.models;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.chat.ChatListActivity;

/**
 * Created by Martin on 24.06.2016.
 */
public class ChatItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;

    public ChatItemHolder(View itemView){
        super(itemView);
        mView = itemView;
    }
    public void setTitle(String title){
        TextView titleField = (TextView) mView.findViewById(R.id.title_name);
        titleField.setText(title);
    }
    public void setLastMessage(String lastMessage){
        TextView lastMessageField = (TextView) mView.findViewById(R.id.last_message);
        lastMessageField.setText(lastMessage);
    }

    @Override
    public void onClick(View v) {
    }
}
