package at.fhooe.mc.android.board;

import android.media.Image;

import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

/**
 * BoardData is the container for the post-Items. It also got Getter- and Setter-Methods
 * The empty constructor is used from Firebase, else it cant get the items from the Database.
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

}
