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

    public MagData(Float posX, Float posY, float x, float y, float z) {
        time = 0;
        this.posX = posX;
        this.posY = posY;
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
