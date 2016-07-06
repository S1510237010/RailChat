package at.fhooe.mc.android.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.firebase.database.ServerValue;




/**
 * Created by Martin on 24.06.2016.
 */
public class ChatItemModel {
    private String title;
    private String lastMessage;
    private Long timestamp;


    public ChatItemModel() {

    }

    public ChatItemModel(String title, String lastMessage) {
        this.title = title;
        this.lastMessage = lastMessage;
        this.timestamp = System.currentTimeMillis();



    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setTimestamp() {
        this.timestamp = System.currentTimeMillis();
    }

    public String getTitle() {
        return title;
    }


    public String getLastMessage() {
        return lastMessage;
    }

    public Long getCreatedTimestamp() {
        return timestamp;
    }




}
