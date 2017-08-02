package io.github.yongjang.automagnetometer;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;

/**
 * Created by YongJang on 2017-08-02.
 */

public class MagneticMeasureView extends AppCompatActivity {
    String DEFAULT_PATH = Environment.getExternalStorageDirectory().getPath();
    String IMAGE_FILE = "hall2.png";
    ImageView imageView;
    Button startButton, endButton;
    Button measureButton, cancelButton;

    public MagneticMeasureView() {
        System.out.println("Constructor!!!12312");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magnetic_measure_layout);

        imageView = (ImageView) findViewById(R.id.hallView);
        final CustomPhotoAttacher customPhotoAttacher = new CustomPhotoAttacher(imageView);
        customPhotoAttacher.mySetTextViews((TextView)findViewById(R.id.valueX), (TextView)findViewById(R.id.valueY));

        //****이미지 띄우는 부분****//
        customPhotoAttacher.setPointLayout((FrameLayout) findViewById(R.id.pointlayout));
        customPhotoAttacher.setMapLayout((FrameLayout) findViewById(R.id.maplayout));
        customPhotoAttacher.setButtonGroup((ViewGroup) findViewById(R.id.hidden_panelSE));
        customPhotoAttacher.setMeasureGroup((ViewGroup) findViewById(R.id.hidden_panelMC));
        customPhotoAttacher.setContext(getApplicationContext());
        /**===========================*/
        // StartSmartAnimation.startAnimation(findViewById(R.id.hallView), AnimationType.BounceInUp, 500, 0, true);

        startButton = (Button) findViewById(R.id.start_button);
        endButton = (Button) findViewById(R.id.end_button);
        measureButton = (Button) findViewById(R.id.measure_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Start Button Touched.");
                customPhotoAttacher.startButtonPushed();
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("End Button Touched.");
                customPhotoAttacher.endButtonPushed();
            }
        });

        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Measure Button Touched.");
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cancel Button Touched.");
                customPhotoAttacher.cancelButtonPushed();
            }
        });
    }
}
