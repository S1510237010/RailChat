package hagenberg.fh_ooe.at.railchat;

import com.firebase.client.Firebase;

/**
 * Created by Anna on 04.05.2016.
 */
public class RailChat extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
