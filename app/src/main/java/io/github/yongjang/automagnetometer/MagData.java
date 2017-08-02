package io.github.yongjang.automagnetometer;

import android.support.annotation.Nullable;

/**
 * Created by YongJang on 2017-08-01.
 */

public class MagData {
    private int time;
    private Float posX = null;
    private Float posY = null;
    private float x;
    private float y;
    private float z;
    private double abs;

    public MagData(Float posX, Float posY, float x, float y, float z, double abs) {
        time = 0;
        this.posX = posX;
        this.posY = posY;
        this.x = x;
        this.y = y;
        this.z = z;
        this.abs = abs;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosX(float x) {
        this.posX = x;
    }

    public void setPosY(float y) {
        this.posY = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public double getAbs() {
        return abs;
    }

}
