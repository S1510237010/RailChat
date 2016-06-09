package at.fhooe.mc.android.login;

import android.app.Activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import at.fhooe.mc.android.R;

public class login_hello extends Activity {
    private ViewFlipper mViewFlipper;
    private GestureDetector mGestureDetector;

    int[] resources = {
            R.drawable.login_hello_first,
            R.drawable.login_hello_second,
            R.drawable.login_hello_third,
    };

    //Custom GestureDetector innerclass to register swipes to next View
    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                mViewFlipper.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                mViewFlipper.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_hello);

        //Get ViewFlipper & GestureDetector
        mViewFlipper = (ViewFlipper) findViewById(R.id.login_hello_viewflipper);
        CustomGestureDetector customGDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(this, customGDetector);

        //Add images to Flipper
        for (int i = 0; i < resources.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(resources[i]);
            mViewFlipper.addView(iv);
        }

        //Set Animations
        mViewFlipper.setInAnimation(this, android.R.anim.fade_in);
        mViewFlipper.setOutAnimation(this, android.R.anim.fade_out);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
