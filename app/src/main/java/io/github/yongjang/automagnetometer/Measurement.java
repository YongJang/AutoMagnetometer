package io.github.yongjang.automagnetometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by YongJang on 2017-08-01.
 */

public class Measurement extends Thread implements SensorEventListener{

    SensorManager sensorManager = null;
    Sensor sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    private Point startPoint;
    private Point endPoint;
    private double valueX;
    private double valueY;
    private double valueZ;
    private double valueAbs;

    public Measurement() {
        if (sensorMagnetic != null) {
            sensorManager.registerListener(this, sensorMagnetic, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {

    }

    public void run() {

    }

}
