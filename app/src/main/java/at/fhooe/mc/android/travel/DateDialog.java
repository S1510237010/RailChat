package at.fhooe.mc.android.travel;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import at.fhooe.mc.android.R;

/**
 * This class sets up a DialogFragment to pick a date and when a date is picked, the picked Date will
 * be written in a certain Textview.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText textDate;

    public DateDialog(){

    }

    @SuppressLint("ValidFragment")
    public DateDialog(EditText text){
        textDate = text;
    }



    public Dialog onCreateDialog(Bundle savedInstance){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }



    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = day + "-" + (month + 1) + "-" + year;
        textDate.setText(date);
    }
}
