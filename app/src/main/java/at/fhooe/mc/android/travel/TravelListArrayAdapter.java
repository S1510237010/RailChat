package at.fhooe.mc.android.travel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import at.fhooe.mc.android.R;

/**
 * Created by Anna on 06.06.2016.
 */
public class TravelListArrayAdapter extends ArrayAdapter<TravelListItem> {


    public TravelListArrayAdapter(Context context) {
        super(context, -1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            Context c = getContext();
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_travel_list_item, null);
        }

        final TravelListItem tl = getItem(position);

        if (tl != null){
            TextView tv = null;
            tv = (TextView) convertView.findViewById(R.id.travel_list_item_to);
            tv.setText(tl.getTo());

            tv = (TextView) convertView.findViewById(R.id.travel_list_item_from);
            tv.setText(tl.getFrom());

            tv = (TextView) convertView.findViewById(R.id.travel_list_item_date);
            tv.setText(tl.getDate());

            tv = (TextView) convertView.findViewById(R.id.travel_list_item_time);
            tv.setText(tl.getTime());

//            tv = (TextView) convertView.findViewById(R.id.travel_list_item_trainNumber);
//            tv.setText(tl.getTrainNumber());
        }

        return convertView;
    }
}
