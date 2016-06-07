package at.fhooe.mc.android.travel;

import android.app.LauncherActivity;

import java.util.Date;

/**
 * Created by Anna on 27.05.2016.
 */
public class TravelListItem extends LauncherActivity.ListItem {


    private Date date;
    private int trainNumber;
    private String to, from;
    private String time;
    private int persons;

    public TravelListItem(){

    }


    public TravelListItem(String _to, String _from, int _trainnumber, int _persons, Date _date, String _time){
        date = _date;
        trainNumber = _trainnumber;
        to = _to;
        from = _from;
        time = _time;
        persons = _persons;
    }


    public Date getDate(){
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


}
