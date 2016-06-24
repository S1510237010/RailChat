package at.fhooe.mc.android.models;

/**
 * Created by Martin on 24.06.2016.
 */
public class ChatItemModel {
    private String title;
    private String lastMessage;
    private int timeStamp;
    public ChatItemModel(){

    }
    public ChatItemModel(String title, String lastMessage, int timeStamp){
        this.title = title;
        this.lastMessage = lastMessage;
        this.timeStamp =timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
