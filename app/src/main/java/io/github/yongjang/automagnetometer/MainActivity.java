package io.github.yongjang.automagnetometer;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.hallView);
        CustomPhotoAttacher customPhotoAttacher = new CustomPhotoAttacher(imageView);
        customPhotoAttacher.mySetTextViews((TextView)findViewById(R.id.valueX), (TextView)findViewById(R.id.valueY));

        //****이미지 띄우는 부분****//
        customPhotoAttacher.setPointLayout((LinearLayout) findViewById(R.id.pointlayout));
        customPhotoAttacher.setContext(getApplicationContext());
        /**===========================*/
        StartSmartAnimation.startAnimation(findViewById(R.id.hallView), AnimationType.BounceInUp, 500, 0, true);
    }
}
