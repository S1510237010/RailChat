package at.fhooe.mc.android.board;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import at.fhooe.mc.android.R;
import at.fhooe.mc.android.main_menu.MainMenu;

/**
 * Created by Bastian on 05.07.2016.
 */
public class NewItem extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_new);

        Button b;
        b = (Button)findViewById(R.id.board_new_button_add);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.board_new_button_back);
        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.board_new_button_add : {
                EditText title = (EditText)findViewById(R.id.board_new_user_title);
                String s = title.getText().toString();
//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                String user = auth.getCurrentUser().getDisplayName();
                BoardData data = new BoardData(s, com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser());


                DatabaseReference newRef = MainMenu.database.getDatabase().getReference();
                newRef = newRef.child("Boards").child(String.valueOf(Board.rjID)).push();//.setValue(data);
                data.setID(newRef.getKey());
                newRef.setValue(data);

                Toast.makeText(this, "BoardItem was successfully added", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_board);

                finish();
            } break;
            case R.id.board_new_button_back : {
                finish();
            } break;
            default: {
                Toast.makeText(this,"Buton Click not found",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
