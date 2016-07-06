package at.fhooe.mc.android.models;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import at.fhooe.mc.android.R;

/**
 * Created by Martin on 24.06.2016.
 *Holder for Messages
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

    /**
     * When User is sender than represent message right and in other color
     * @param isSender ture when user is sender
     */
    public void setIsSender(boolean isSender){
        RelativeLayout mMessageContainer = (RelativeLayout) mView.findViewById(R.id.message_container);
        LinearLayout mMessageItem = (LinearLayout) mView.findViewById(R.id.message_item);
        int color;
        if(isSender){
            mMessageContainer.setGravity(Gravity.END);
            color = ContextCompat.getColor(mView.getContext(), R.color.shadowColor);
        }else{
            mMessageContainer.setGravity(Gravity.START);
            color = ContextCompat.getColor(mView.getContext(), R.color.false_red);
        }
        ((GradientDrawable) mMessageItem.getBackground()).setColor(color);

    }
}
