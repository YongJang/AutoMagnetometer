package io.github.yongjang.automagnetometer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.ArrayList;

/**
 * Created by YongJang on 2017-07-24.
 */

public class CustomPhotoAttacher  extends PhotoViewAttacher implements View.OnTouchListener, View.OnLongClickListener{
    private TextView textViewX;
    private TextView textViewY;
    private FrameLayout pointLayout;
    private FrameLayout mapLayout;
    private Context context;
    private ArrayList<Point> pointList = new ArrayList<Point>();
    private ImageView tmpImage;
    private static float touchPointX = 0;
    private static float touchPointY = 0;

    public CustomPhotoAttacher(ImageView imageView) { super(imageView); }

    public void mySetTextViews(TextView x, TextView y) {
        this.textViewX = x;
        this.textViewY = y;
    }

    public void setPointLayout(FrameLayout imageView) {
        this.pointLayout = imageView;
    }
    public void setMapLayout(FrameLayout imageView) {
        this.mapLayout = imageView;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public boolean onLongClick(View v) {
        System.out.println("CALL --> imageView.setOnTouchListener::onLongClick()");
        return false;
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {

        super.setOnClickListener(listener);
    }

    public boolean onTouch(View view, MotionEvent event) {
        FrameLayout frameLayout = mapLayout;
        RectF rf;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            rf = getDisplayRect();
            for (int i = 0; i < pointList.size(); i++) {
                frameLayout.removeView(pointList.get(i).getImageView());
            }
            // 주석
            float x = (event.getX() - rf.left) / getScale();
            float y = (event.getY() - rf.top) / getScale();
            TextView valueX = textViewX;
            TextView valueY = textViewY;
            valueX.setText(Float.toString(x));
            valueY.setText(Float.toString(y));
            System.out.println("////" + rf.left + "///" + rf.top + "////");

            /** 이미지 띄우는 부분 */
            touchPointX = event.getX();
            touchPointY = event.getY();



            // frameLayout.addView(image);
            // tmpImage = image;


        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            rf = getDisplayRect();
            try {
                float x = (event.getX() - rf.left) / getScale();
                float y = (event.getY() - rf.top) / getScale();
                ImageView image = new ImageView(context);
                image.setBackgroundResource(R.mipmap.bluecircle);
                image.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
                image.getLayoutParams().width = 50;
                image.getLayoutParams().height = 50;
                Point p = new Point(image, x, y);

                int clickWidth = 5;
                if ((event.getX() <= touchPointX + clickWidth && event.getX() >= touchPointX - clickWidth) && (event.getY() <= touchPointY + clickWidth && event.getY() >= touchPointY - clickWidth))
                    pointList.add(p);

                for (int i = 0; i < pointList.size(); i++) {
                    pointList.get(i).getImageView().setX((pointList.get(i).getX() * getScale() + rf.left));
                    pointList.get(i).getImageView().setY((pointList.get(i).getY() * getScale() + rf.top));

                }

                for (int i = 0; i < pointList.size(); i++) {
                    frameLayout.addView(pointList.get(i).getImageView());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            frameLayout.invalidate();
        }
        System.out.println("*****************" + getScale() + "*****************");

        System.out.println("CALL --> imageView.setOnTouchListener::onTouch()");

        return super.onTouch(view, event);
    }
}
