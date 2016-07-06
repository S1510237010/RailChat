package at.fhooe.mc.android.models;

/**
 * Created by Martin on 01.07.2016.
 */
public class FriendItemModel {
    String name;
    String uid;
    public FriendItemModel(){
    }

    public FriendItemModel(String name, String uid){
        this.name = name;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
