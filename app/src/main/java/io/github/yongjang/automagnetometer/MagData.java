package io.github.yongjang.automagnetometer;

/**
 * Created by YongJang on 2017-08-01.
 */

public class MagData {
    private int time;
    private float x;
    private float y;
    private float z;

    public MagData(float x, float y, float z) {
        time = 0;
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
