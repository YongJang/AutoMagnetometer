package io.github.yongjang.automagnetometer;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * Created by HoJu on 2017-07-24.
 */

public class CustomPhotoAttacher  extends PhotoViewAttacher implements View.OnTouchListener, View.OnLongClickListener{
    private TextView textViewX;
    private  TextView textViewY;
    public CustomPhotoAttacher(ImageView imageView) {
        super(imageView);
    }

    public void mySetTextViews(TextView x, TextView y) {
        this.textViewX = x;
        this.textViewY = y;
    }

    public boolean onLongClick(View v) {
        return false;
    }

    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = (int) event.getX();
            float y = (int) event.getY();
            TextView valueX = textViewX;
            TextView valueY = textViewY;
            valueX.setText(Float.toString(x));
            valueY.setText(Float.toString(y));
            System.out.println("12313123123123212");
        }
        System.out.println("CALL --> imageView.setOnTouchListener::onTouch()");
        return super.onTouch(view, event);
    }
}
