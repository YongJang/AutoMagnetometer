package io.github.yongjang.automagnetometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by YongJang on 2017-08-01.
 */

public class Measurement extends Thread implements SensorEventListener {

    SensorManager sensorManager = null;
    Sensor sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    private Point startPoint;
    private Point endPoint;
    private float values[];
    private float valueX;
    private float valueY;
    private float valueZ;
    private double valueAbs;
    // private Vector<MagData>
    // private List<MagData> dataList = Collections.synchronizedList()
    private boolean stopFlag = false;

    public Measurement() {
        if (sensorMagnetic != null) {
            sensorManager.registerListener(this, sensorMagnetic, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        values = event.values;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD :
                valueX = values[0];
                valueY = values[1];
                valueZ = values[2];
        }
    }

    public void run() {
        try {
            while (!stopFlag) {

                Thread.sleep(100);
            }
        } catch(Exception e) {

        }
    }

}
