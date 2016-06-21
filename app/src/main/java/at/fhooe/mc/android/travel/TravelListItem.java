package at.fhooe.mc.android.travel;

import android.app.LauncherActivity;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;

/**
 * Created by Anna on 27.05.2016.
 */
public class TravelListItem extends LauncherActivity.ListItem {


    private static final String TAG = "TravelListItem";
    private String date;
    private int trainNumber;
    private String to, from;
    private String time;
    private int persons;
    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;

    public TravelListItem(){
    }


    public TravelListItem(String _to, String _from, int _trainnumber, int _persons, String _date, String _time){
        date = _date;
        trainNumber = _trainnumber;
        to = _to;
        from = _from;
        time = _time;
        persons = _persons;
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


    public boolean equals(TravelListItem other){

        if (!this.date.equals(other.date)){
            return false;
        }

        if (this.trainNumber != other.trainNumber){
            return false;
        }

        if (!this.to.equals(other.to)){
            return false;
        }

        if (!this.from.equals(other.from)){
            return false;
        }

        if (!this.time.equals(other.time)){
            return false;
        }
        return true;
    }


}
