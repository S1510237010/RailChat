package at.fhooe.mc.android.board;

import android.media.Image;

import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

/**
 * Created by Bastian on 07.06.2016.
 */
class BoardData {
    protected String title, link;
    protected Image image;
    protected String user;
    protected int upvote, downvote, hours, minutes;
    protected String id;
    //Comments ...

    BoardData() {
        //used by Firebase to get Data from Database
    }

    BoardData(String _title, String _link, FirebaseUser _user) {
        title = _title;
        link = _link;
        user = _user.getDisplayName();
        Calendar date = Calendar.getInstance();
        hours = date.get(Calendar.HOUR_OF_DAY); //Hour of day -> 24h format
        minutes = date.get(Calendar.MINUTE);
        image = null;
        upvote = downvote = 0;
    }

    BoardData(String _title, FirebaseUser _user) {
        title = _title;
        link = null;
        user = _user.getDisplayName();
        Calendar date = Calendar.getInstance();
        hours = date.get(Calendar.HOUR_OF_DAY);
        minutes = date.get(Calendar.MINUTE);
        image = null;
        upvote = downvote = 0;
    }

    public String getTitle() {return title;}
    public String getLink() {return link;}
    public Image getImage() {return image;}
    public String getUser() {return user;}
    public int getUpvote() {return upvote;}
    public int getDownvote() {return downvote;}
    public int getHours() {return hours;}
    public int getMinutes() {return minutes;}
    public void setID(String _s) {id = _s;}
    public String getID() {return id;}


//    public Map<String,Object> toMap() {
//
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("Title: ", getTitle());
//        result.put("Link: ", getLink());
//        result.put("User", getUserName());
//        result.put("Date", date);
//
//        return result;
//    }
}
