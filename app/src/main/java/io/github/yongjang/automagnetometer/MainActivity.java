package io.github.yongjang.automagnetometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 *   @author YongJang
 *   https://github.com/YongJang/AutoMagnetometer
 *   ====== used libraries ======
 *   https://github.com/chrisbanes/PhotoView
 *   https://github.com/ratty3697/android-smart-animation-library
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToMagneticMeasureView(View view) {
        Intent intent = new Intent(getApplicationContext(), MagneticMeasureView.class);
        startActivity(intent);
    }

    public void goToMagneticMeasureViewWithPDR(View view) {
        Intent intent = new Intent(getApplicationContext(), MagneticMeasureViewWithPDR.class);
        startActivity(intent);
    }
}
