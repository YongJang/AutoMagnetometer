package io.github.yongjang.automagnetometer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongJang on 2017-08-02.
 */

public class MagneticMeasureView extends AppCompatActivity {
    String DEFAULT_PATH = Environment.getExternalStorageDirectory().getPath();
    String IMAGE_FILE = "hall2.png";
    ImageView imageView;
    Button startButton, endButton;
    Button measureButton, cancelButton;
    Button measureFinishButton;
    ArrayList<List<MagData>> fullDataList = new ArrayList<List<MagData>>();
    ArrayList<MagData> magDataSet;
    Measurement measurementManager;
    int fileIndex = 0;
    int inputNumberX = -1;

    public MagneticMeasureView() { }

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
        customPhotoAttacher.setFinishGroup((ViewGroup) findViewById(R.id.hidden_panelF));
        customPhotoAttacher.setContext(getApplicationContext());
        /**===========================*/
        // StartSmartAnimation.startAnimation(findViewById(R.id.hallView), AnimationType.BounceInUp, 500, 0, true);

        startButton = (Button) findViewById(R.id.start_button);
        endButton = (Button) findViewById(R.id.end_button);
        measureButton = (Button) findViewById(R.id.measure_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        measureFinishButton = (Button) findViewById(R.id.measure_finish_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Start Button Touched.");
                customPhotoAttacher.startButtonPushed(v);
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("End Button Touched.");
                customPhotoAttacher.endButtonPushed(v);
            }
        });

        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Measure Button Touched.");
                magDataSet = customPhotoAttacher.measureButtonPushed();
                measurementManager = new Measurement(getApplicationContext());
                int tempIndex = magDataSet.size();
                fileIndex = tempIndex / 2;
                measurementManager.initialStartEndPointData(magDataSet.get(tempIndex - 2), magDataSet.get(tempIndex - 1));
                measurementManager.setMagneticActivateFlag(true);
                measurementManager.setMagTextView((TextView) findViewById(R.id.magValueX), (TextView) findViewById(R.id.magValueY),
                        (TextView) findViewById(R.id.magValueZ), (TextView) findViewById(R.id.magValueAbs));
                //measurementManager.run();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cancel Button Touched.");
                customPhotoAttacher.cancelButtonPushed();
            }
        });

        measureFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cancel Button Touched.");
                List<MagData> magDataList = null;
                if (measurementManager != null) {
                    measurementManager.setMagneticActivateFlag(false);
                    measurementManager.measureFinish();
                    magDataList = measurementManager.getDataList();
                }
                customPhotoAttacher.finishButtonPushed();
                System.out.println("=====================================");
                if (magDataList != null) {
                    for (int i = 0; i < magDataList.size(); i++) {
                        System.out.println("Pos X : " + magDataList.get(i).getPosX() + " / Pos Y : " + magDataList.get(i).getPosY() +" :: "
                                + magDataList.get(i).getX() + ", "
                                + magDataList.get(i).getY() + ", "
                                + magDataList.get(i).getZ() + ", "
                                + magDataList.get(i).getAbs());
                    }
                }
                System.out.println("=====================================");
                fullDataList.add(magDataList); // not used yet.

                // write data on file
                FileManager fm = new FileManager();
                fm.initialResultFile("testData-" + fileIndex + ".csv");
                fm.writeDataList(magDataList);
            }
        });
    }
}
