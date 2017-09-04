package io.github.yongjang.automagnetometer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by YongJang on 2017-09-02.
 */

public class MagneticMeasureViewWithPDR extends AppCompatActivity{
    public MagneticMeasureViewWithPDR() { }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdr_magnetic_measure_layout);
    }
}

