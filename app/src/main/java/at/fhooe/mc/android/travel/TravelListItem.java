package at.fhooe.mc.android.travel;

import android.app.LauncherActivity;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.MotionEvent;
import android.view.View;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Anna on 27.05.2016.
 */
public class TravelListItem extends LauncherActivity.ListItem {


    private static final String TAG = "TravelListItem";
    private String ID;
    private String date;
    private int trainNumber;
    private String to, from;
    private String time;
    private int persons;
    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;

    public TravelListItem(String id){
        this.ID = id;
    }

    public TravelListItem(String _ID, String _to, String _from, int _trainnumber, int _persons, String _date, String _time){
        date = _date;
        trainNumber = _trainnumber;
        to = _to;
        from = _from;
        time = _time;
        persons = _persons;
        ID = _ID;
    }


    public String getDate(){
        return date;
    }

    public int getTrainNumber(){
        return trainNumber;
    }

    public String getTo(){
        return to;
    }

    public String getFrom(){
        return from;
    }

    public String getTime(){
        return time;
    }

    public int getPersons(){
        return persons;
    }

    public String getID(){
        return ID;
    }

    public boolean equals(TravelListItem other){

        if (!this.ID.equals(other.ID)){
            return false;
        }

        return true;
    }


}
