package io.github.yongjang.automagnetometer;

import android.graphics.RectF;
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
        System.out.println("CALL --> imageView.setOnTouchListener::onLongClick()");
        return false;
    }

    public boolean onTouch(View view, MotionEvent event) {
        RectF rf;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            rf = getDisplayRect();
            float x = ((int) event.getX() - rf.left) / getScale();
            float y = ((int) event.getY() - rf.top) / getScale();
            TextView valueX = textViewX;
            TextView valueY = textViewY;
            valueX.setText(Float.toString(x));
            valueY.setText(Float.toString(y));
            System.out.println("////" + rf.left + "///" + rf.top + "////");
        }

        System.out.println("*****************" + getScale() + "*****************");

        System.out.println("CALL --> imageView.setOnTouchListener::onTouch()");
        return super.onTouch(view, event);
    }
}
