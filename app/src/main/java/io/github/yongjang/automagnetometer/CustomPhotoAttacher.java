package io.github.yongjang.automagnetometer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * Created by HoJu on 2017-07-24.
 */

public class CustomPhotoAttacher  extends PhotoViewAttacher implements View.OnTouchListener, View.OnLongClickListener{
    private TextView textViewX;
    private TextView textViewY;
    private LinearLayout pointLayout;
    private Context context;

    public CustomPhotoAttacher(ImageView imageView) {
        super(imageView);
    }

    public void mySetTextViews(TextView x, TextView y) {
        this.textViewX = x;
        this.textViewY = y;
    }

    public void setPointLayout(LinearLayout imageView) {
        this.pointLayout = imageView;
    }

    public void setContext(Context context) {
        this.context = context;
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

            /** 이미지 띄우는 부분 */
             LinearLayout linearLayout = pointLayout;
             ImageView image = new ImageView(context);
             image.setBackgroundResource(R.mipmap.ic_launcher);
             linearLayout.addView(image);
            /*==================================*/
        }

        System.out.println("*****************" + getScale() + "*****************");

        System.out.println("CALL --> imageView.setOnTouchListener::onTouch()");
        return super.onTouch(view, event);
    }
}
