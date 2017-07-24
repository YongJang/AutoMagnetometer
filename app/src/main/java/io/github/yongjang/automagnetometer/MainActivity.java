package io.github.yongjang.automagnetometer;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;

public class MainActivity extends AppCompatActivity {

    String DEFAULT_PATH = Environment.getExternalStorageDirectory().getPath();
    String IMAGE_FILE = "hall2.png";
    ImageView imageView;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.hallView);
        CustomPhotoAttacher customPhotoAttacher = new CustomPhotoAttacher(imageView);
        customPhotoAttacher.mySetTextViews((TextView)findViewById(R.id.valueX), (TextView)findViewById(R.id.valueY));
    }
}
