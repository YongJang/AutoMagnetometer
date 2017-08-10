package io.github.yongjang.automagnetometer;

import android.widget.ImageView;

/**
 * Created by YongJang on 2017-08-01.
 */

public class Point {
    private ImageView imageView;
    private float x;
    private float y;


    public Point(ImageView imageView, float x, float y) {
        this.imageView = imageView;
        this.x = x;
        this.y = y;
    }

    public void setPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
