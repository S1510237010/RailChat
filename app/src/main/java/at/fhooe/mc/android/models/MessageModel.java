package at.fhooe.mc.android.models;

/**
 * Created by Martin on 24.06.2016.
 */
public class MessageModel {
    String name;
    String message;

    public MessageModel(){

    }

    public MessageModel(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
