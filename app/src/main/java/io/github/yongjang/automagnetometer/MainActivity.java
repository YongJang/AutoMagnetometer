package io.github.yongjang.automagnetometer;

import android.hardware.SensorEventListener;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;

/**
 *   @author YongJang
 *   https://github.com/YongJang/AutoMagnetometer
 *   ====== used libraries ======
 *   https://github.com/chrisbanes/PhotoView
 *   https://github.com/ratty3697/android-smart-animation-library
 */

public class MainActivity extends AppCompatActivity {

    String DEFAULT_PATH = Environment.getExternalStorageDirectory().getPath();
    String IMAGE_FILE = "hall2.png";
    ImageView imageView;
    Button startButton, endButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.hallView);
        final CustomPhotoAttacher customPhotoAttacher = new CustomPhotoAttacher(imageView);
        customPhotoAttacher.mySetTextViews((TextView)findViewById(R.id.valueX), (TextView)findViewById(R.id.valueY));

        //****이미지 띄우는 부분****//
        customPhotoAttacher.setPointLayout((FrameLayout) findViewById(R.id.pointlayout));
        customPhotoAttacher.setMapLayout((FrameLayout) findViewById(R.id.maplayout));
        customPhotoAttacher.setButtonGroup((ViewGroup) findViewById(R.id.hidden_panel));
        customPhotoAttacher.setContext(getApplicationContext());
        /**===========================*/
        StartSmartAnimation.startAnimation(findViewById(R.id.hallView), AnimationType.BounceInUp, 500, 0, true);

        startButton = (Button) findViewById(R.id.start_button);
        endButton = (Button) findViewById(R.id.end_button);

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
    }
}
