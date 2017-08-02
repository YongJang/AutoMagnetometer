package io.github.yongjang.automagnetometer;

/**
 * Created by YongJang on 2017-08-01.
 */

public class MagData {
    private int time;
    private float posX;
    private float posY;
    private float x;
    private float y;
    private float z;

    public MagData(float posX, float posY, float x, float y, float z) {
        time = 0;
        this.posX = posX;
        this.posY = posY;
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
