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
        // dataList.add(startPointData);
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

                    valueX = Float.parseFloat(String.format("%.1f", valueX));
                    valueY = Float.parseFloat(String.format("%.1f", valueY));
                    valueZ = Float.parseFloat(String.format("%.1f", valueZ));
                    double tmpAbs = Math.sqrt(valueX * valueX + valueY * valueY + valueZ * valueZ);

                    if (time >= TIME_INDEX) {
                        time = 0;
                        System.out.println("x : " + valueX + ", y : " + valueY + ", z : " + valueZ);
                        addData(valueX, valueY, valueZ, tmpAbs);
                    }
                    time++;

                    textViewMagValueX.setText("Magnetic X : " + valueX);
                    textViewMagValueY.setText("Magnetic Y : " + valueY);
                    textViewMagValueZ.setText("Magnetic Z : " + valueZ);

                    textViewMagValueAbs.setText("Magnetic abs : " + Double.toString(tmpAbs));

                    break;
            }
        }
    }

    private void addData(float x, float y, float z, double abs) {
        dataList.add(new MagData(null, null, x, y, z, abs));
    }

    public void measureFinish() {
        float startPointX = startPointData.getPosX();
        float startPointY = startPointData.getPosY();
        float endPointX = endPointData.getPosX();
        float endPointY = endPointData.getPosY();

        // dataList.add(endPointData);
        int arraySize = dataList.size();
        float intervalX;
        float intervalY;

        if (startPointX >= endPointX) {
            intervalX = (startPointX - endPointX) / (arraySize -1);
        } else {
            intervalX = (endPointX - startPointX) / (arraySize -1);
        }
        if (startPointY >= endPointY) {
            intervalY = (startPointY - endPointY) / (arraySize -1);
        } else {
            intervalY = (endPointY - startPointY) / (arraySize -1);
        }

        System.out.println("********************************");
        System.out.println("startPoint x : " + startPointX + " // endPoint x :" + endPointX);
        System.out.println("startPoint y : " + startPointY + " // endPoint y :" + endPointY);
        System.out.println("interval x : " + intervalX);
        System.out.println("interval y : " + intervalY);
        System.out.println("********************************");

        dataList.get(0).setPosX(startPointX);
        dataList.get(0).setPosY(startPointY);
        dataList.get(arraySize - 1).setPosX(endPointX);
        dataList.get(arraySize - 1).setPosY(endPointY);

        for (int i = 1; i < arraySize - 1; i++) {
            dataList.get(i).setPosX(startPointX + (intervalX * i));
            dataList.get(i).setPosY(startPointY + (intervalY * i));
        }
    }

    public List<MagData> getDataList() {
        return dataList;
    }

}
