package io.github.yongjang.automagnetometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by YongJang on 2017-08-01.
 */

public class Measurement implements SensorEventListener {

    SensorManager sensorManager = null;
    Sensor sensorMagnetic = null;
    private Context context;
    private TextView textViewMagValueX;
    private TextView textViewMagValueY;
    private TextView textViewMagValueZ;
    private TextView textViewMagValueAbs;
    private MagData startPointData;
    private MagData endPointData;
    private float values[];
    private float valueX;
    private float valueY;
    private float valueZ;
    private double valueAbs;
    private boolean magneticActivateFlag = false;
    private List<MagData> dataList = Collections.synchronizedList(new ArrayList<MagData>());
    private final int TIME_INDEX = 2; // Recording per approximately 60 * ( TIME_INDEX ) ms
    private int time = TIME_INDEX;

    // private Vector<MagData>
    // private List<MagData> dataList = Collections.synchronizedList()
    private boolean stopFlag = false;

    public Measurement(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (sensorMagnetic != null) {
            sensorManager.registerListener(this, sensorMagnetic, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void setMagTextView(TextView magValueX, TextView magValueY, TextView magValueZ, TextView magValueAbs) {
        this.textViewMagValueX = magValueX;
        this.textViewMagValueY = magValueY;
        this.textViewMagValueZ = magValueZ;
        this.textViewMagValueAbs = magValueAbs;
    }

    public void setMagneticActivateFlag(boolean flag) {
        this.magneticActivateFlag = flag;
    }

    public void initialStartEndPointData(MagData startPointData, MagData endPointData) {
        this.startPointData = startPointData;
        this.endPointData = endPointData;
        dataList.add(startPointData);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        if (magneticActivateFlag) {
            values = event.values;
            switch (event.sensor.getType()) {
                case Sensor.TYPE_MAGNETIC_FIELD:
                    valueX = values[0];
                    valueY = values[1];
                    valueZ = values[2];

                    if (time >= TIME_INDEX) {
                        time = 0;
                        System.out.println("x : " + valueX + ", y : " + valueY + ", z : " + valueZ);
                        addData(valueX, valueY, valueZ);
                    }
                    time++;

                    textViewMagValueX.setText("Magnetic X : " + String.format("%.1f", valueX));
                    textViewMagValueY.setText("Magnetic Y : " + String.format("%.1f", valueY));
                    textViewMagValueZ.setText("Magnetic Z : " + String.format("%.1f", valueZ));
                    double tmpAbs = Math.sqrt(valueX * valueX + valueY * valueY + valueZ * valueZ);
                    textViewMagValueAbs.setText("Magnetic abs : " + Double.toString(tmpAbs));

                    break;
            }
        }
    }

    private void addData(float x, float y, float z) {
        dataList.add(new MagData(null, null, x, y, z));
    }

}
