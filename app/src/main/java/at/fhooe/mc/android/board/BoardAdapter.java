package at.fhooe.mc.android.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.main_menu.MainMenu;

/**
 * Created by Bastian on 07.06.2016.
 */
class BoardAdapter extends ArrayAdapter<BoardData> {
    public BoardAdapter(Context context) {
        super(context, R.layout.activity_board_item);
    }

    @Override
    public View getView(int _pos, View _v, ViewGroup _p) {
        //return super.getView(position, convertView, parent);
        if (_v == null) {
            Context c = getContext();
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            _v = inflater.inflate(R.layout.activity_board_item,null);
            _v = inflater.inflate(R.layout.activity_board_item,_p,false);
        }
        final BoardData data = getItem(_pos);
        if (data != null) {
            TextView tv = null;
            tv = (TextView)_v.findViewById(R.id.board_title);
            tv.setText(data.getTitle());
            tv = (TextView)_v.findViewById(R.id.board_info);
            StringBuffer buffer = new StringBuffer();
            buffer.append("submitted by " + data.getUser() + " on " + data.getHours() + ":");
            if (data.getMinutes() < 10) buffer.append("0");
            buffer.append(data.getMinutes());
            tv.setText(buffer.toString());

            tv = (TextView)_v.findViewById(R.id.board_link);
            if (data.getLink() != null) tv.setText(data.getLink());
            else tv.setVisibility(View.INVISIBLE);

            tv = (TextView)_v.findViewById(R.id.board_number);
            tv.setText(String.valueOf(data.upvote-data.downvote));

            final int rjID = Board.rjID;
            Button b;
            b = (Button)_v.findViewById(R.id.board_upvote);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference ref = MainMenu.database.getDatabase().getReference().child("Boards").child(String.valueOf(rjID)).child(data.getID());
                    Map<String,Object> newUpvote = new HashMap<String,Object>();
                    newUpvote.put("upvote",data.getUpvote()+1);
                    ref.updateChildren(newUpvote);
                }
            });
            b = (Button)_v.findViewById(R.id.board_downvote);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference ref = MainMenu.database.getDatabase().getReference().child("Boards").child(String.valueOf(rjID)).child(data.getID());
                    Map<String,Object> newDownvote= new HashMap<String,Object>();
                    newDownvote.put("downvote",data.getDownvote()+1);
                    ref.updateChildren(newDownvote);
                }
            });


//            tv = (TextView)_v.findViewById(R.id.board_title);
//            tv.setText(data.getTitle());
//            tv = (TextView)_v.findViewById(R.id.bottom);
//            tv.setText(data.getBottom());
//            ImageView iv = (ImageView)_v.findViewById(R.id.imageView);
//            iv.setImageResource(data.getImage());
//            Button b = (Button)_v.findViewById(R.id.button);
//            b.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getContext() ,"Das Land " + data.getBottom() + " ist toll",Toast.LENGTH_SHORT).show();
//                }
//            });
        }


        return _v;
    }
}
