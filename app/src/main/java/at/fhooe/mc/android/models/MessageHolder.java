package at.fhooe.mc.android.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import at.fhooe.mc.android.R;

/**
 * Created by Martin on 24.06.2016.
 */
public class MessageHolder extends RecyclerView.ViewHolder {
    View mView;
    public MessageHolder(View itemView){
        super(itemView);
        mView = itemView;
    }
    public void setName(String name){
        TextView nameField = (TextView) mView.findViewById(R.id.message_user);
        nameField.setText(name);
    }
    public void setMessage(String message){
        TextView messageField = (TextView) mView.findViewById(R.id.message_text);
        messageField.setText(message);
    }
}
