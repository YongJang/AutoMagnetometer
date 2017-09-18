package io.github.yongjang.automagnetometer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by YongJang on 2017-07-24.
 */

public class CustomPhotoAttacher  extends PhotoViewAttacher implements View.OnTouchListener, View.OnLongClickListener{
    private TextView textViewX;
    private TextView textViewY;
    private FrameLayout pointLayout;
    private FrameLayout mapLayout;
    private ViewGroup buttonGroup;
    private ViewGroup measureGroup;
    private ViewGroup finishGroup;
    private Context context;
    private ArrayList<Point> startPointList = new ArrayList<Point>();
    private ArrayList<Point> endPointList = new ArrayList<Point>();
    private ArrayList<MagData> magDataSet = new ArrayList<MagData>();
    private ImageView tmpImage;
    private static float touchPointX = 0;
    private static float touchPointY = 0;
    private Point tempPoint;
    private int startButtonFlag = 0;
    private float inputNumberX;
    private float inputNumberY;
    private RectF tempRF;
    private float tempScale;
    private boolean dialogCancelFlag;


    public CustomPhotoAttacher(ImageView imageView) { super(imageView); }

    public void mySetTextViews(TextView x, TextView y) {
        this.textViewX = x;
        this.textViewY = y;
    }

    public void setPointLayout(FrameLayout imageView) {
        this.pointLayout = imageView;
    }
    public void setMapLayout(FrameLayout imageView) {
        this.mapLayout = imageView;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public void setButtonGroup(ViewGroup viewGroup) { this.buttonGroup = viewGroup; }
    public void setMeasureGroup(ViewGroup viewGroup) { this.measureGroup = viewGroup; }
    public void setFinishGroup(ViewGroup viewGroup) { this.finishGroup = viewGroup; }

    public boolean onLongClick(View v) {
        System.out.println("CALL --> imageView.setOnTouchListener::onLongClick()");
        return false;
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {

        super.setOnClickListener(listener);
    }

    public boolean onTouch(View view, MotionEvent event) {
        FrameLayout frameLayout = mapLayout;
        RectF rf;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            rf = getDisplayRect();
            tempRF = rf;
            tempScale = getScale();
            frameLayout.removeView(tmpImage);
            for (int i = 0; i < startPointList.size(); i++) {
                frameLayout.removeView(startPointList.get(i).getImageView());
            }
            for (int i = 0; i < endPointList.size(); i++) {
                frameLayout.removeView(endPointList.get(i).getImageView());
            }
            // 주석

            System.out.println("////" + rf.left + "///" + rf.top + "////");

            /** 이미지 띄우는 부분 */
            touchPointX = event.getX();
            touchPointY = event.getY();



            // frameLayout.addView(image);
            // tmpImage = image;


        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            rf = getDisplayRect();
            tempRF = rf;
            tempScale = getScale();
            try {
                float x = (event.getX() - rf.left) / getScale();
                float y = (event.getY() - rf.top) / getScale();
                TextView valueX = textViewX;
                TextView valueY = textViewY;

                valueX.setText(Float.toString(x));
                valueY.setText(Float.toString(y));
                /***  pdr맵과 지도의 배율(4.5배)를 맞춰주기 위해 임시적 수정
                valueX.setText(Float.toString(x * 4.5f));
                valueY.setText(Float.toString(y * 4.5f));
                */
                ImageView image = new ImageView(context);
                image.setBackgroundResource(R.mipmap.whitecircle);
                image.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
                // point image size
                image.getLayoutParams().width = 50;
                image.getLayoutParams().height = 50;

                tempPoint = new Point(image, x, y);

                int clickWidth = 5;
                if ((event.getX() <= touchPointX + clickWidth && event.getX() >= touchPointX - clickWidth) && (event.getY() <= touchPointY + clickWidth && event.getY() >= touchPointY - clickWidth)){
                    // pointList.add(p);
                    tmpImage = tempPoint.getImageView();
                    tempPoint.getImageView().setX((tempPoint.getX() * getScale() + rf.left));
                    tempPoint.getImageView().setY((tempPoint.getY() * getScale() + rf.top));
                    frameLayout.addView(tempPoint.getImageView());

                    if (startButtonFlag == 0) {
                        Animation bottomUp = AnimationUtils.loadAnimation(context, R.anim.bottom_up);
                        ViewGroup hiddenPannel = buttonGroup;
                        hiddenPannel.startAnimation(bottomUp);
                        hiddenPannel.setVisibility(View.VISIBLE);
                    }
                } else {
                    tempPoint = null;
                }


                for (int i = 0; i < startPointList.size(); i++) {
                    startPointList.get(i).getImageView().setX((startPointList.get(i).getX() * getScale() + rf.left));
                    startPointList.get(i).getImageView().setY((startPointList.get(i).getY() * getScale() + rf.top));

                }
                for (int i = 0; i < endPointList.size(); i++) {
                    endPointList.get(i).getImageView().setX((endPointList.get(i).getX() * getScale() + rf.left));
                    endPointList.get(i).getImageView().setY((endPointList.get(i).getY() * getScale() + rf.top));
                }

                for (int i = 0; i < startPointList.size(); i++) {
                    frameLayout.addView(startPointList.get(i).getImageView());
                }
                for (int i = 0; i < endPointList.size(); i++) {
                    frameLayout.addView(endPointList.get(i).getImageView());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            frameLayout.invalidate();
        }
        System.out.println("*****************" + getScale() + "*****************");

        System.out.println("CALL --> imageView.setOnTouchListener::onTouch()");
        // mapLayout.invalidate();
        return super.onTouch(view, event);
    }

    public int startButtonPushed(final View v) {
        if (startButtonFlag != 0 || tempPoint == null) {
            return -1;
        }

        dialogCancelFlag = false;

        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Set Position");

        LinearLayout dialogLayout = new LinearLayout(v.getContext());
        dialogLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputX = new EditText(v.getContext());
        inputX.setHint("input x position value");
        inputX.setInputType(InputType.TYPE_CLASS_TEXT);
        inputX.setText(String.valueOf(tempPoint.getX()));
        final EditText inputY = new EditText(v.getContext());
        inputY.setHint("input y position value");
        inputY.setInputType(InputType.TYPE_CLASS_TEXT);
        inputY.setText(String.valueOf(tempPoint.getY()));

        dialogLayout.addView(inputX);
        dialogLayout.addView(inputY);
        builder.setView(dialogLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputNumberX = Float.parseFloat(inputX.getText().toString());
                inputNumberY = Float.parseFloat(inputY.getText().toString());
                tempPoint.setPoint(inputNumberX, inputNumberY);
                tempPoint.getImageView().setX(getAbsolutePositionX(inputNumberX, tempRF, tempScale));
                tempPoint.getImageView().setY(getAbsolutePositionY(inputNumberY, tempRF, tempScale));
                tempPoint.getImageView().setBackgroundResource(R.mipmap.bluecircle);
                startPointList.add(tempPoint);
                tempPoint = null;
                startButtonFlag = 1;
                v.invalidate();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogCancelFlag = true;
                startButtonFlag = 0;
                tempPoint = null;
                dialog.cancel();
            }
        });
        builder.show();

        if (dialogCancelFlag) {  return -1;  }
        else return startPointList.size();
    }

    public int endButtonPushed(final View v) {
        if (startButtonFlag != 1 || (startPointList.size() <= endPointList.size()) || tempPoint == null) {
            return -1;
        }

        dialogCancelFlag = false;

        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Set Position");

        LinearLayout dialogLayout = new LinearLayout(v.getContext());
        dialogLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputX = new EditText(v.getContext());
        inputX.setHint("input x position value");
        inputX.setInputType(InputType.TYPE_CLASS_TEXT);
        inputX.setText(String.valueOf(tempPoint.getX()));
        final EditText inputY = new EditText(v.getContext());
        inputY.setHint("input y position value");
        inputY.setInputType(InputType.TYPE_CLASS_TEXT);
        inputY.setText(String.valueOf(tempPoint.getY()));

        dialogLayout.addView(inputX);
        dialogLayout.addView(inputY);
        builder.setView(dialogLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputNumberX = Float.parseFloat(inputX.getText().toString());
                inputNumberY = Float.parseFloat(inputY.getText().toString());
                tempPoint.setPoint(inputNumberX, inputNumberY);
                tempPoint.getImageView().setX(getAbsolutePositionX(inputNumberX, tempRF, tempScale));
                tempPoint.getImageView().setY(getAbsolutePositionY(inputNumberY, tempRF, tempScale));
                tempPoint.getImageView().setBackgroundResource(R.mipmap.redcircle);
                endPointList.add(tempPoint);
                tempPoint = null;
                startButtonFlag = 0;
                Animation bottomDown = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
                Animation bottomUp = AnimationUtils.loadAnimation(context, R.anim.bottom_up);
                ViewGroup hiddenPannelSE = buttonGroup;
                ViewGroup hiddenPannelMC = measureGroup;
                hiddenPannelSE.startAnimation(bottomDown);
                hiddenPannelSE.setVisibility(View.INVISIBLE);
                hiddenPannelMC.startAnimation(bottomUp);
                hiddenPannelMC.setVisibility(View.VISIBLE);
                v.invalidate();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogCancelFlag = true;
                startButtonFlag = 1;
                tempPoint = null;
                dialog.cancel();
            }
        });
        builder.show();

        if (dialogCancelFlag) {  return -1;  }
        else return endPointList.size();
    }

    public ArrayList<MagData> measureButtonPushed() {
        if (startPointList.size() <= 0 || endPointList.size() <= 0) {
            return null;
        }
        int startIndex = startPointList.size() - 1;
        int endIndex = endPointList.size() - 1;
        Point startPointLastElement = startPointList.get(startIndex);
        Point endPointLastElement = endPointList.get(endIndex);

        MagData startPoint = new MagData(startPointLastElement.getX(), startPointLastElement.getY(), 0, 0, 0, 0);
        MagData endPoint = new MagData(endPointLastElement.getX(), endPointLastElement.getY(), 0, 0, 0, 0);

        magDataSet.add(startPoint);
        magDataSet.add(endPoint);

        Animation bottomDown = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
        Animation bottomUp = AnimationUtils.loadAnimation(context, R.anim.bottom_up);

        ViewGroup hiddenPannelF = finishGroup;
        ViewGroup hiddenPannelMC = measureGroup;

        hiddenPannelMC.startAnimation(bottomDown);
        hiddenPannelMC.setVisibility(View.INVISIBLE);
        hiddenPannelF.startAnimation(bottomUp);
        hiddenPannelF.setVisibility(View.VISIBLE);

        return  magDataSet;
    }

    public int cancelButtonPushed() {
        FrameLayout frameLayout = mapLayout;
        if (startPointList.size() <= 0 || endPointList.size() <= 0) {
            return -1;
        }
        int startIndex = startPointList.size() - 1;
        int endIndex = endPointList.size() - 1;
        frameLayout.removeView(startPointList.get(startIndex).getImageView());
        frameLayout.removeView(endPointList.get(endIndex).getImageView());
        startPointList.remove(startIndex);
        endPointList.remove(endIndex);
        startButtonFlag = 0;
        Animation bottomDown = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
        ViewGroup hiddenPannelMC = measureGroup;
        ViewGroup hiddenPannelSE = buttonGroup;
        hiddenPannelMC.startAnimation(bottomDown);
        hiddenPannelMC.setVisibility(View.INVISIBLE);
        hiddenPannelSE.startAnimation(bottomDown);
        return endIndex;
    }

    public void finishButtonPushed() {
        startButtonFlag = 0;
        Animation bottomDown = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
        ViewGroup hiddenPannelF = finishGroup;
        ViewGroup hiddenPannelSE = buttonGroup;
        hiddenPannelF.startAnimation(bottomDown);
        hiddenPannelF.setVisibility(View.INVISIBLE);
        hiddenPannelSE.startAnimation(bottomDown);
    }

    public float getAbsolutePositionX(float x, RectF rf, float scale) {
        return x * scale + rf.left;
    }

    public float getAbsolutePositionY(float y, RectF rf, float scale) {
        return y * scale + rf.top;
    }
}
