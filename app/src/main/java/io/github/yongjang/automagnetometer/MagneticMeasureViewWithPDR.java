package io.github.yongjang.automagnetometer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by YongJang on 2017-09-02.
 */

/**********************************************/
/***************TEST CODE IMPORT***************/
/**********************************************/
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


import static android.os.SystemClock.uptimeMillis;
import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.sin;
/**********************************************/
/**********************************************/

public class MagneticMeasureViewWithPDR extends AppCompatActivity implements SensorEventListener{
    public MagneticMeasureViewWithPDR() { }
    /**********************************************/
    /********TEST INITIAL CODE START***************/
    /**********************************************/
    public static int cnt = 0;
    public String averxx, averyy = null;
    public int averageX, averageY, XXX, YYY, ANG, ID = 0;
    public double averx, avery = 0;

    private EditText Inputeditx,Inputedity,InputeditID;
    private TextView tView, tView1, tView2, tView3;
    private Button resetBtn, btn, inputbtn,button;


    private float x, y, z;
    private float accpx, accpy, accpz, accpzd, accpzdd, accpzddd, laccpzddd, accp,
            pitch = 0, yaw = 0, roll = 0, qpitch = 0, qyaw = 0, qroll = 0;
    private double upti = 0;
    private int  c, k, l, ll, ckl, cd, cdd, cddd, cyd, cydd, cyddd, cyyd, cyydd, cyyddd, cyyyd, cyyydd, cyyyddd, tc, m, lc = 0,count,total_count, cc, cn,d=0,input=0
            ,ptotal_count,pcount,rtotal_count,rcount,fourC=0;
    private int i=1,j=0;
    private int YY,PP,RR=0;
    private float minaz, maxiaz, lminaz, lmaxiaz;
    private String uptime, uptim;
    private float q0,q1,q2,q3;
    private String E0,E1,E2,E3,E4,E5,E6,E7,E8,E9,E00=null;
    private double e0,e1,e2,e3,e4,e5,e6,e7,e8,e9,e00,e01,e02,e03,e04,e05,e06,e07,e08,e09,e10,e11,e12,e13,e14,e15,e16,e17,e18,e19,e20,e21,e22,
            e23,e24,e25,e26,e27,
            e28,e29,e30=0;
    private int sTate=0;
    private String xxx,yyy;
    String Now, Now1, now2, Now3, X, Y, Z, AX, AY, AZ,RQY, GX, GY, GZ, GT, UP, MMM, EEE, dD = null;
    String es,WSE,WSE1, ms,ts = null;
    String QQY,QQP,QQR=null;
    private String sXXX,sYYY,sID;

    long[] SEC = new long[2000];
    long now, now1;
    private double mid, start, end, lend, de, dd, det, dete, detec, detect, tdetect, ldetect, ltdetect = 0;
    private double ss,mm,ee,lee;
    private double minpB,maxp1,minp1,lmaxp1,lminp1;
    long SEC1 = 0;
    private SensorManager sensorManager;
    float[] mMatrix = new float[3];
    float[] magneticField = new float[3];
    float[] agMatrix = new float[3];
    float[] qMatrix=new float[5];

    float anglex;
    //pitch
    private float anp=-180,anp1=180,lminp=-95,minp=-95 ,maxp=-95,lmaxp=-95;
    private double psB,ps,pm,pe,lpe;
    private int pB,pc,pk,pl,pll,pcn,pckl,pdm,pd,numBp,numLp=0;
    private int ptc,plc;
    //roll
    private float anr=-90,anr1=90, maxr,minr,lmaxr,lminr;
    private double rsB,rs,rm,re,lre;
    private int rB,rc,rk,rl,rll,rcn,rckl,rdm,rd,numBr,numLr=0;
    private double minrB;





    float angle, angle1, angle2,angle3,angle4,angle5;



    private static final String Tag = "LogCatTest";

    WebView wvLayout0401v3;

    private static final String[] PERMISSIONS = new String[] {Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE};


    //서버
    private Socket socket;
    BufferedReader socket_in;
    PrintWriter socket_out;
    String data=null;
    TextView output;
    /***********************TEST INITIAL CODE END***********************/
    /** File I / O Setting declare **/
    String PATH = Environment.getExternalStorageDirectory().getPath();
    File magnetometerResultFile;
    FileOutputStream os;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.pdr_magnetic_measure_layout);

        /** File I / O Setting initial **/
        magnetometerResultFile = new File(PATH, "PDR test data.csv");
        try {
            os = new FileOutputStream(magnetometerResultFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            os.write("#X,#Y,#Z,#abs,#PosX,#PosY\r\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

    /**********************************************/
    /************TEST onCreate() CODE START********/
    /**********************************************/
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.pdr_magnetic_measure_layout_included);
        setContentView(R.layout.pdr_magnetic_measure_layout);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);





        resetBtn = (Button) findViewById(R.id.resetBtn);
        btn = (Button) findViewById(R.id.write);
        button=(Button)findViewById(R.id.button);

        InputeditID=(EditText)findViewById(R.id.IDedittext);
        Inputeditx=(EditText)findViewById(R.id.Xedittext);
        Inputedity=(EditText)findViewById(R.id.Yedittext);


        tView = (TextView) findViewById(R.id.cntView);
        tView1 = (TextView) findViewById(R.id.cntView1);
        wvLayout0401v3 = (WebView) findViewById(R.id.Layout0401v3);
        //웹뷰 초기설정 변수
        WebSettings webSettings = wvLayout0401v3.getSettings();
        //웹뷰 초기설정
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        //웹뷰 초기정보 보이기
       /* wvLayout0401v3.loadUrl("http://hojuworld.dlinkddns.com:88/HYUNDAI/CORE_DATA/default.html");*/
        //초기정보 선택 버튼 리스너
     /*   wvLayout0401v3.goBack();
        wvLayout0401v3.loadUrl("http://hojuworld.dlinkddns.com:88/HYUNDAI/CORE_DATA/map_KUCE_F02.html");*/
        wvLayout0401v3.goBack();
        // wvLayout0401v3.loadUrl("http://it.korea.ac.kr/HYUNDAI/CORE_DATA/map_HDRND_F01.html");
       /* wvLayout0401v3.goBack();
        wvLayout0401v3.loadUrl("http://hojuworld.dlinkddns.com:88/HYUNDAI/CORE_DATA/map_KUCE_F01.html");*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sID=String.valueOf(InputeditID.getText());
                Log.d("sID",""+sID);
                wvLayout0401v3.loadUrl("http://it.korea.ac.kr/Automagnetometer/automagnetometer.html");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sXXX=String.valueOf(Inputeditx.getText());
                        sYYY=String.valueOf(Inputedity.getText());
                        //   ID=Integer.parseInt(sID);
                        XXX=Integer.parseInt(sXXX);
                        YYY = Integer.parseInt(sYYY);
                        wvLayout0401v3.loadUrl("javascript:androidBridge (" + XXX + ", " + YYY + ",'#000000')");


                    }
                }, 500) ;



//data => server
                /*
                 data=ID+" "+XXX+" "+YYY;
                Thread worker = new Thread() {

                    public void run() {
                        try {
                            socket = new Socket("52.79.79.153", 52222);
                            socket_out = new PrintWriter(socket.getOutputStream(), true);
                            socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            while (true) {
                                //data = socket_in.readLine();
                                output.post(new Runnable() {
                                    public void run() {
                                        output.setText(data);
                                    }
                                });
                            }
                        } catch (Exception e) {

                        }

                        Log.d("shout",""+data);
                        if(data != null)
                            socket_out.println(data);


                    }

                };
                worker.start();*/

                //wvLayout0401v3.loadUrl("javascript:androidBridge (" + XXX + ", " + YYY + ",'#000000')");
            }
        });


    /**************TEST onCreate() CODE END********/
    }

    /**********************************************/
    /************TEST OTHERS CODE START************/
    /**********************************************/


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_GAME);//중력빼고 가속도 delay_nor,ui.game,fastest
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);//방향센서
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);//중력+
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);//자기장
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);//Rotation
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER://중력
                agMatrix = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMatrix = event.values.clone();
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                qMatrix= event.values.clone();
                break;
        }

        if(qMatrix!=null){



            q0=qMatrix[3];
            q1=qMatrix[0];
            q2=qMatrix[1];
            q3=qMatrix[2];



        }

        if (mMatrix != null && agMatrix != null) {
            float[] orientation = new float[3];
            float[] R = new float[16]; //
            float[] I = new float[16];
            magneticField[0] = mMatrix[0];
            magneticField[1] = mMatrix[1];
            magneticField[2] = mMatrix[2];
            //가속도 센서, 자기 센서의 값을 바탕으로 회전 행렬을 계산한다.
            SensorManager.getRotationMatrix(R, I, agMatrix, mMatrix);//I는 기울기 R은 디바이스 시스템의 좌표계에서 세상 좌표계로, 그냥 방향 센서가 주는 데이터 직접 사용 시:완전한 결과 값이기 때문에 데이터의 좌표 기준을 변경 가공할 수 없다는 단점,그러나 회전 메트릭스를 가공하여 원하는 결과 도출 장점
            //디바이스의 방향에 따라 회전 행렬을 계산한다.
            SensorManager.getOrientation(R, orientation);
            yaw = orientation[0];
            pitch = orientation[1];
            roll = orientation[2]; // orientation contains: azimut, pitch and roll z,x,y


            //라디안에서 각도로 변환한다.
            angle = (float) Math.floor(Math.toDegrees(orientation[0]));//degree로 바꿈
            angle1 = (float) Math.floor(Math.toDegrees(orientation[1]));
            angle2 = (float) Math.floor(Math.toDegrees(orientation[2]));
            //각도의 범위를 0~360도로 조정한다.

            if (angle >= 0) {
                anglex = angle;
            } else if (angle < 0) {
                anglex = 360 + angle;
            }
            if(anglex>=0){
                anglex=360-anglex;
            }
            if(anglex>=205){
                anglex=anglex-205;
            }else if(anglex<205){
                anglex=360-(205-anglex);
            }
          /*  if( anglex>=0&& anglex<=90){
                anglex=90-anglex+67;
            } else if(anglex>90&& anglex<=180){
                anglex=360-(90-180+anglex)+67;
            }else if(anglex>180&& anglex<=270){
                anglex=360-(180-270+anglex)+67;
            }else if(anglex>=270&& anglex<=360){
                anglex=360-(270-360+anglex)+67;
            }*/
            tView.setText(String.valueOf(anglex));

        }


        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            now = System.currentTimeMillis();
            uptime = Long.toString(now);
            uptim = uptime.substring(6);
            upti = Double.valueOf(uptim);

            qyaw= (float) atan2(2.0 * (q3 * q0 + q1 * q2), 1-2.0*(q0*q0+q1*q1));//3 qyaw+180
            qpitch=(float)-atan2(2 * (q0 * q1 + q3 * q2), q3 * q3 + q0 * q0 - q1 * q1 - q2 * q2);;//4
            qroll=(float)asin(2 * (q0 * q2 - q3 * q1));


            x = event.values[0];
            y = event.values[1];
            z = event.values[2];



            accpx = (float) (
                    cos(-qyaw) * cos(-qroll) * x
                            + (cos(-qyaw) * sin(-qroll) * sin(qpitch)
                            - sin(-qyaw) * cos(qpitch)
                    ) * y
                            + (cos(-qyaw) * sin(-qroll) * cos(qpitch)
                            + sin(-qyaw) * sin(qpitch)
                    ) * z

            );



            accpy = (float) (sin(-qyaw) * cos(-qroll) * x //yaw roll pitch
                    + (sin(-qyaw) * sin(-qroll) * sin(qpitch)
                    + cos(-qyaw) * cos(qpitch)
            ) * y
                    + (sin(-qyaw) * sin(-qroll) * cos(qpitch)
                    - cos(-qyaw) * sin(qpitch)
            ) * z);


            accpz = (float) (-sin(qroll) * x
                    + (cos(qroll) * sin(-qpitch)
            ) * y
                    + (cos(qroll) * cos(-qpitch)
            ) * z);//radian
          /*  angle2=(float)Math.toDegrees(qyaw);*/
            angle5 = (float) Math.toDegrees(qroll);
            angle3 = (float) Math.toDegrees(qyaw) + 180;
            angle4 = (float) Math.toDegrees(qpitch);

            UP = UP + " " + Double.toString(upti);

            QQR = QQR + "  " + Float.toString(angle5);

            QQY = QQY + "  " + Float.toString(angle3);
              /* Log.d("QY"," "+angle3);*/
            QQP = QQP + "  " + Float.toString(angle4);

            if(sTate==0||sTate==1) {

                if (c == 0 && k == 0 && l == 0) {
                    if (accpz > 1.2) {
                        c = 1;

                        if (maxiaz < accpz) {
                            start = upti;
                            maxiaz = accpz;
                            c = 0;
                            de = upti;
                            accpzd = accpz;
                            accp = accpz;
                            if (maxiaz > 10) {
                                m++;
                            }
                        }
                    }
                }//최댓값, 문제 없음

                if (c == 1 && k == 0 && l == 0) {
                    lmaxiaz = 0;
                    end = 0;
                    detect = 0;
                    tdetect = 0;
                    if (accpz > 1.2) {
                        if (maxiaz < accpz) {
                            start = upti;
                            maxiaz = accpz;
                            if (maxiaz > 10) {
                                m++;
                            }
                        }
                    }
                    if (accpz > 0) {
                        if (cd == 0 && cdd == 0 && cddd == 0) {
                            if (accpzd < accpz) {
                                accpzd = accpz;
                                de = upti;
                            } else if (accpzd > accpz) {
                                cd = 1;
                            }
                        }
                        if (cd == 1 && cdd == 0 && cddd == 0) {
                            if (accpzd > accpz) {
                                accpzd = accpz;
                            } else {
                                cdd = 1;
                            }
                        }
                        if (cd == 1 && cdd == 1 && cddd == 0) {
                            if (accpzd < accpz) {
                                accpzd = accpz;
                                det = upti;
                            } else {
                                cddd = 1;
                            }
                        }
                    }
                    if (det - de > 200 && cd == 1 && cdd == 1 && cddd == 1) {
                        m++;
                    }//W

                    if (accpz < -0.5) {
                        k = 1;
                        if (minaz > accpz) {
                            k = 0;
                            minaz = accpz;
                            mid = upti;
                            dete = upti;
                            accpzdd = accpz;
                        }
                        if (minaz < -10) {
                            m++;
                        }
                    } //최솟값


                    if (accp > 0 && accpz > 0) {
                        if (Math.abs(accp - accpz) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    } else if (accp > 0 && accpz < 0) {
                        if (Math.abs(accp - accpz) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    } else if (accp < 0 && accpz > 0) {
                        if (Math.abs(accpz - accp) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    } else if (accp < 0 && accpz < 0) {
                        if (Math.abs(accp - accpz) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    }
                }//최솟값


                if (c == 1 && k == 1 && l == 0) {
                    if (accpz < -1.2) {
                        if (minaz > accpz) {
                            mid = upti;
                            minaz = accpz;
                        }
                        if (minaz < -10) {
                            m++;
                        }
                    }
                    if (accpz < 0) {
                        if (cyd == 0 && cydd == 0 && cyddd == 0) {
                            if (accpzdd > accpz) {
                                accpzdd = accpz;
                                dete = upti;
                            } else if (accpzdd < accpz) {
                                cyd = 1;
                            }
                        }
                        if (cyd == 1 && cydd == 0 && cyddd == 0) {
                            if (accpzdd < accpz) {
                                accpzdd = accpz;
                            } else {
                                cydd = 1;
                            }
                        }
                        if (cyd == 1 && cydd == 1 && cyddd == 0) {
                            if (accpzdd > accpz) {
                                accpzdd = accpz;
                                detec = upti;
                            } else {
                                cyddd = 1;
                            }
                        }
                    }
                    if (detec - dete > 200 && cyd == 1 && cydd == 1 && cyddd == 1) {

                        m++;
                    }//W

                    if (accpz > 0.5) {
                        l = 1;
                        if (lmaxiaz < accpz) {
                            l = 0;
                            lmaxiaz = accpz;
                            end = upti;
                            detect = upti;
                            accpzddd = accpz;
                        }
                        if (lmaxiaz > 10) {
                            m++;
                        }

                    }//최댓값
                    if (accp > 0 && accpz > 0) {
                        if (Math.abs(accp - accpz) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    } else if (accp > 0 && accpz < 0) {
                        if (Math.abs(accp - accpz) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    } else if (accp < 0 && accpz > 0) {
                        if (Math.abs(accpz - accp) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    } else if (accp < 0 && accpz < 0) {
                        if (Math.abs(accp - accpz) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    }
                }//최댓값


                if (c == 1 && k == 1 && l == 1) {
                    if (accpz > 1.2) {
                        if (lmaxiaz < accpz) {
                            end = upti;
                            lmaxiaz = accpz;
                        }
                        if (lmaxiaz > 10){
                            m++;
                        }
                    }

                    if (accpz > 0) {
                        if (cyyd == 0 && cyydd == 0 && cyyddd == 0) {
                            if (accpzddd < accpz) {
                                accpzddd = accpz;
                                detect = upti;
                            } else if (accpzddd > accpz) {
                                cyyd = 1;
                            }
                        }
                        if (cyyd == 1 && cyydd == 0 && cyyddd == 0) {
                            if (accpzddd > accpz) {
                                accpzddd = accpz;
                            } else {
                                cyydd = 1;
                            }
                        }
                        if (cyyd == 1 && cyydd == 1 && cyyddd == 0) {
                            if (accpzddd < accpz) {
                                accpzddd = accpz;
                                tdetect = upti;
                            } else {
                                cyyddd = 1;
                            }
                        }
                    }
                    if (tdetect - detect > 200 && cyyd == 1 && cyydd == 1 && cyyddd == 1) {
                        m++;
                    }//W

                    if (accpz < 0.5) {
                        cn = 1;

                    }
                    if (accp > 0 && accpz > 0) {
                        if (Math.abs(accp - accpz) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    } else if (accp > 0 && accpz < 0) {
                        if (Math.abs(accp - accpz) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    } else if (accp < 0 && accpz > 0) {
                        if (Math.abs(accpz - accp) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    } else if (accp < 0 && accpz < 0) {
                        if (Math.abs(accp - accpz) < 3) {
                            accp = accpz;
                        } else {
                            m++;
                            accp = accpz;
                        }
                    }
                }//마지막
                if (c == 1 && k == 1 && l == 1 && cn == 1) {
                    if (maxiaz + lmaxiaz - minaz > 30) {

                        m++;
                    } else if (maxiaz + lmaxiaz - minaz > 4) {
                        if ((end - start > 200) && (end - start < 800)) {
                            ckl = 1;
                        } else {
                            m++;
                        }
                    }
                    if (ckl == 1 && m == 0) {
                        if (total_count == 0) {

                            total_count = 2;
                            count = 2;

                            maxiaz = 0;
                            maxiaz = lmaxiaz;
                            //tView3.setText(String.valueOf(end - start) + " " + String.valueOf(mid - start));

                            start = 0;
                            start = end;
                            de = 0;
                            det = 0;
                            cd = 0;
                            cdd = 0;
                            cddd = 0;
                            accpzd = 0;

                            k = 0;
                            minaz = 0;
                            mid = 0;
                            dete = 0;
                            detec = 0;
                            cyd = 0;
                            cydd = 0;
                            cyddd = 0;
                            accpzdd = 0;

                            l = 0;
                            cn = 0;
                            cyyd = 0;
                            cyydd = 0;
                            cyyddd = 0;
                            accpzddd = 0;
                            tc = 1;
                            cc = 2;
                            ckl = 0;
                            m = 0;

                            sTate=1;

                            if (tdetect == 0) {
                                de = detect;
                            } else {
                                de = tdetect;
                            }//w
                        } else if (total_count > 1) {
                            total_count++;
                            count++;

                            maxiaz = 0;
                            maxiaz = lmaxiaz;
                            //tView3.setText(String.valueOf(end - start) + " " + String.valueOf(mid - start));
                            start = 0;
                            start = end;
                            de = 0;
                            det = 0;
                            cd = 0;
                            cdd = 0;
                            cddd = 0;
                            accpzd = 0;

                            k = 0;
                            minaz = 0;
                            mid = 0;
                            dete = 0;
                            detec = 0;
                            cyd = 0;
                            cydd = 0;
                            cyddd = 0;
                            accpzdd = 0;

                            l = 0;
                            cyyd = 0;
                            cyydd = 0;
                            cyyddd = 0;
                            accpzddd = 0;
                            cn = 0;
                            tc = 1;
                            cc = 1;
                            ckl = 0;
                            m = 0;

                            sTate=1;
                            if (tdetect == 0) {
                                de = detect;
                            } else {
                                de = tdetect;
                            }//w
                        }
                    } else {
                        c = 0;
                        maxiaz = 0;
                        start = 0;
                        de = 0;
                        det = 0;
                        cd = 0;
                        cdd = 0;
                        cddd = 0;
                        accpzd = 0;
                        k = 0;
                        minaz = 0;
                        mid = 0;
                        dete = 0;
                        detec = 0;
                        cyd = 0;
                        cydd = 0;
                        cyddd = 0;
                        accpzdd = 0;
                        l = 0;
                        lmaxiaz = 0;
                        end = 0;
                        detect = 0;
                        tdetect = 0;
                        cyyd = 0;
                        cyydd = 0;
                        cyyddd = 0;
                        accpzddd = 0;
                        cn = 0;
                        ckl = 0;
                        m = 0;

                        sTate=0;

                    }
                    if (tc == 1) {
                        tc = 0;
                        double rad = Math.toRadians(anglex);

                        averx = 78 * sin(rad);


                        Log.d(Tag, " " + averx);
                        avery = 78 * cos(rad);

                        Log.d("y", " " + avery);

                        Log.d("ang", " " + anglex);
                        lc = 1;

                        if ((averx > 4 || avery > 4 || averx < -4 || avery < -4)&&lc==1) {
                            lc = 0;
                            averageX = averageX / 4;
                            averx = cc * averx / 4;
                            XXX = XXX + (int) averx;
                            Log.d("최종값x", "" + XXX);
                            averageY = averageY / 4;
                            avery = cc * avery / 4;
                            YYY = YYY + (int) avery;
                            //Log.d("최종값Y", "" + YYY);Thread worker = new Thread() {
                            //data => server로 옮기기(재형)
                            data=ID+" "+XXX+" "+YYY;
                      /*  Thread worker = new Thread() {

                            public void run() {
                                try {
                                    socket = new Socket("52.79.79.153", 52222);
                                    socket_out = new PrintWriter(socket.getOutputStream(), true);
                                    socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    while (true) {
                                        //data = socket_in.readLine();
                                        output.post(new Runnable() {
                                            public void run() {
                                                output.setText(data);
                                            }
                                        });
                                    }
                                } catch (Exception e) {

                                }

                                Log.d("shout",""+data);
                                if(data != null)
                                    socket_out.println(data);


                            }

                        };
                        worker.start();
*/
                            /*** 실제 걸음횟수가 카운트 된다고 판단 내리는 부분?? */
                            try {
                                String data = magneticField[0] + "," + magneticField[1] + "," + magneticField[2] + ","
                                        + Math.sqrt(magneticField[0] * magneticField[0] + magneticField[1] * magneticField[1] + magneticField[2] * magneticField[2])
                                        + "," + XXX + "," + YYY + "\r\n";
                                os.write(data.getBytes());
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            wvLayout0401v3.loadUrl("javascript:androidBridge (" + XXX + ", " + YYY + ",'#000000')");
                            cc = 0;
                        }
                    }

                }

                // tView.setText(String.valueOf(anglex));

                tView1.setText(String.valueOf(total_count+ptotal_count));
            }//state==1 finish
            i++;


// angle Count(pitch)
       /* if(sTate==0||sTate==2) {

            if (pB == 0 && pc == 0 && pk == 0 && pl == 0 && pll == 0) {
                if (-90 > angle4 && angle4 >= -150 || 40 <= angle4 && angle4 < 90) {
                    pB = 1;
                    if (anp1 > angle4) {
                        pB = 0;
                        psB = upti;
                        anp1 = angle4;
                        minpB = angle4;

                    }
                }

            }//간단 P 최솟 값
            if (pc == 0 && pk == 0 && pl == 0 && pll == 0) {
                if (-40 >= angle4 && angle4 >= -90 || 90 <= angle4 && angle4 <= 140) {
                    pc = 1;
                    pd = 1;
                    if (anp < angle4) {
                        pc = 0;
                        ps = upti;
                        anp = angle4;
                        maxp = angle4;

                    }
                }

            }//간단 P 최댓 값

            if (pc == 1 && pk == 0 && pl == 0 && pll == 0) {
                if (-90 > angle4 && angle4 >= -150 || 40 <= angle4 && angle4 < 90) {
                    pk = 1;

                    if (anp > angle4) {
                        pk = 0;
                        pm = upti;
                        anp = angle4;
                        minp = angle4;
                    }
                }
            }//간단 P 최솟 값

            if (pc == 1 && pk == 1 && pl == 0 && pll == 0) {
                if (-40 >= angle4 && angle4 >= -90 || 90 <= angle4 && angle4 <= 140) {
                    pl = 1;
                    if (anp < angle4) {
                        pl = 0;
                        pe = upti;
                        anp = angle4;
                        lmaxp = angle4;
                    }
                }
            }//간단 P 2번째 최댓 값

            if (pc == 1 && pk == 1 && pl == 1 && pll == 0) {
                if (-90 > angle4 && angle4 >= -150 || 40 <= angle4 && angle4 < 90) {
                    pll = 1;
                    if (anp > angle4) {
                        pll = 0;
                        lpe = upti;
                        anp = angle4;
                        lminp = angle4;
                    }
                }
            }//간단 P 2번째 최솟값
            if (pc == 1 && pk == 1 && pl == 1 && pll == 1) {

                  pcn = 1;

            }
            if (pc == 1 && pk == 1 && pl == 1 && pll == 1 && pcn == 1) {
                if (pB == 0) {
                  //  if ((200 < pm - ps) && (pm - ps < 1000)) {
                       // if ((20 < Math.abs(maxp - minp)) && (Math.abs(maxp - minp) < 80)) {
                          //  if ((20 < Math.abs(lmaxp - lminp)) && Math.abs(lmaxp - lminp) < 80) {
                                // if(0<=Math.abs(lmaxp-maxp)&&Math.abs(lmaxp-maxp)<= 5){
                                   //if ((200 < lpe - pe) && (lpe - pe < 1000)) {
                                     //if(0<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 5){
                                         pckl = 1;
                                    *//* }else if(10<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 15){
                                         numL=1;
                                     }else{
                                         pdm++;
                                     }*//*
                                  *//* }else if(6<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 10){
                                    numLp=1;
                                 }else{
                                    pdm++;
                                    e23 = 1;
                                    lmaxp1 = (double) lmaxp;
                                    lminp1 = (double) lminp;

                                }*//*
                           *//* }else{
                                     pdm++;
                                 }
                            } else {
                                pdm++;
                                e24 = 1;
                                maxp1 = (double) maxp;
                                minp1 = (double) minp;

                            }*//*
                        *//*} else {
                            pdm++;
                            e25 = 1;
                        }
                    } else {
                        pdm++;
                        e26 = 1;

                    }*//*
                } else if (pB == 1) {
                    *//*if ((200 < pm - ps) && (pm - ps < 1000)) {
                        if ((20 < Math.abs(maxp - minp)) && (Math.abs(maxp - minp) < 80)) {
                            if ((20 < Math.abs(lmaxp - lminp)) && Math.abs(lmaxp - lminp) < 80) {
                                // if(0<=Math.abs(lmaxp-maxp)&&Math.abs(lmaxp-maxp)<= 5){
                                if ((200 < lpe - pe) && (lpe - pe < 1000)) {
                                    if ((200 < ps - psB) && (ps - psB < 1000)) {
                                        if (0 <= Math.abs(minp - minpB) && Math.abs(minp - minpB) <= 20) {
                                            numBp = 1;
                                            e27 = 1;
                                        }
                                    }
                                    //if(0<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 5){
                                    pckl = 1;
                                     }else if(10<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 15){
                                         numL=1;
                                     }else{
                                         pdm++;
                                     }
                                }else if(6<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 10){
                                    if ((200 < ps - psB) && (ps - psB < 1000)) {
                                        if (0 <= Math.abs(minp - minpB) && Math.abs(minp - minpB) <= 20) {
                                            numBp = 1;
                                            e27 = 1;
                                        }
                                    }
                                    numLp=1;
                                }else{
                                    pdm++;
                                    e23 = 1;
                                    lmaxp1 = (double) lmaxp;
                                    lminp1 = (double) lminp;

                                }
                            }else{
                                     pdm++;
                                 }
                            } else {
                                pdm++;
                                e24 = 1;
                                maxp1 = (double) maxp;
                                minp1 = (double) minp;

                            }
                        } else {
                            pdm++;
                            e25 = 1;
                        }
                    } else {
                        pdm++;
                        e26 = 1;

                    }*//*
                }

                if (pckl == 1 && pdm == 0) {
                    if (ptotal_count == 0 || pd == 1) {
                        ptotal_count = ptotal_count + 4 + numBp - numLp;
                        pcount = pcount + 4 + numBp - numLp;


                        cc = 4 + numBp - numLp;
                        ptc = 1;

                        pB = 0;
                        numBp = 0;
                        psB = 0;
                        minpB = 0;
                        numLp = 0;

                        ps = 0;
                        ps = pe;
                        maxp = 0;
                        maxp = lmaxp;

                        pm = 0;
                        pm = lpe;
                        minp = 0;
                        minp = lminp;


                        pcn = 0;
                        pckl = 0;

                        pdm = 0;
                        pd = 0;

                        sTate=2;


                    } else if (ptotal_count > 0 && pd == 0) {
                        ptotal_count = ptotal_count + 2 - numLp;
                        pcount = pcount + 2 - numLp;

                        cc = 2 - numLp;
                        ptc = 1;

                        pB = 0;
                        numBp = 0;
                        psB = 0;
                        minpB = 0;
                        numLp=0;

                        ps = 0;
                        ps = pe;
                        maxp = 0;
                        maxp = lmaxp;

                        pm = 0;
                        pm = lpe;
                        minp = 0;
                        minp = lminp;


                        pcn = 0;
                        pckl = 0;

                        pdm = 0;
                        pd = 0;

                        sTate=2;


                    }
                } else {

                    pB = 0;
                    numBp = 0;
                    psB = 0;
                    minpB = 0;
                    numLp=0;

                    pc = 0;
                    ps = 0;
                    maxp = 0;

                    pk = 0;
                    pm = 0;
                    minp = 0;

                    pl = 0;
                    pe = 0;
                    lmaxp = 0;

                    pll = 0;
                    lpe = 0;
                    lminp = 0;

                    anp = -180;
                    pcn = 0;
                    pckl = 0;

                    pdm = 0;
                    pd = 0;
                    ptc = 0;
                    cc = 0;

                    sTate=0;

                }
                if (ptc == 1) {
                    ptc = 0;

                    pl = 0;
                    pe = 0;
                    lmaxp = 0;

                    pll = 0;
                    lpe = 0;
                    lminp = 0;

                    double rad = Math.toRadians(anglex + 30);

                    averx = 60 * sin(rad);


                    Log.d(Tag, " " + averx);
                    avery = 60 * cos(rad);


                    plc = 1;
                    if ((averx > 4 || avery > 4 || averx < -4 || avery < -4) && plc == 1) {
                        plc = 0;
                        averageX = averageX / 4;
                        averx = cc * averx / 4;
                        XXX = XXX + (int) averx;

                        averageY = averageY / 4;
                        avery = cc * avery / 4;
                        YYY = YYY + (int) avery;
                        wvLayout0401v3.loadUrl("javascript:androidBridge (" + XXX + ", " + YYY + ",'#000000')");
                        cc = 0;
                    }
                }
            }
            tView.setText(String.valueOf(ptotal_count));
            tView1.setText(String.valueOf(total_count+ptotal_count));
        }// state==2*/

//angle count roll
          /*  if(sTate==0||sTate==3) {

                if (rB == 0 && rc == 0 && rk == 0 && rl == 0 && rll == 0) {
                    if (0 >= angle5 && angle5 >= -30 ) {
                        rB = 1;
                        if (anr1 > angle5) {
                            rB = 0;
                            rsB = upti;
                            anr1 = angle5;
                            minrB = angle5;

                        }
                    }

                }//간단 R 최솟 값
                if (rc == 0 && rk == 0 && rl == 0 && rll == 0) {
                    if (30 >= angle5 && angle5 >= -0 ) {
                        rc = 1;
                        rd = 1;
                        if (anr < angle5) {
                            pc = 0;
                            ps = upti;
                            anp = angle4;
                            maxp = angle4;

                        }
                    }

                }//간단 P 최댓 값

                if (pc == 1 && pk == 0 && pl == 0 && pll == 0) {
                    if (-90 > angle4 && angle4 >= -150 || 40 <= angle4 && angle4 < 90) {
                        pk = 1;

                        if (anp > angle4) {
                            pk = 0;
                            pm = upti;
                            anp = angle4;
                            minp = angle4;
                        }
                    }
                }//간단 P 최솟 값

                if (pc == 1 && pk == 1 && pl == 0 && pll == 0) {
                    if (-40 >= angle4 && angle4 >= -90 || 90 <= angle4 && angle4 <= 140) {
                        pl = 1;
                        if (anp < angle4) {
                            pl = 0;
                            pe = upti;
                            anp = angle4;
                            lmaxp = angle4;
                        }
                    }
                }//간단 P 2번째 최댓 값

                if (pc == 1 && pk == 1 && pl == 1 && pll == 0) {
                    if (-90 > angle4 && angle4 >= -150 || 40 <= angle4 && angle4 < 90) {
                        pll = 1;
                        if (anp > angle4) {
                            pll = 0;
                            lpe = upti;
                            anp = angle4;
                            lminp = angle4;
                        }
                    }
                }//간단 P 2번째 최솟값
                if (pc == 1 && pk == 1 && pl == 1 && pll == 1) {

                    pcn = 1;

                }
                if (pc == 1 && pk == 1 && pl == 1 && pll == 1 && pcn == 1) {
                    if (pB == 0) {
                        if ((200 < pm - ps) && (pm - ps < 1000)) {
                            if ((20 < Math.abs(maxp - minp)) && (Math.abs(maxp - minp) < 80)) {
                                if ((20 < Math.abs(lmaxp - lminp)) && Math.abs(lmaxp - lminp) < 80) {
                                    // if(0<=Math.abs(lmaxp-maxp)&&Math.abs(lmaxp-maxp)<= 5){
                                    if ((200 < lpe - pe) && (lpe - pe < 1000)) {
                                        //if(0<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 5){
                                        pckl = 1;
                                     }else if(10<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 15){
                                         numL=1;
                                     }else{
                                         pdm++;
                                     }
                                    }else if(6<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 10){
                                        numL=1;
                                    }else{
                                        pdm++;
                                        e23 = 1;
                                        lmaxp1 = (double) lmaxp;
                                        lminp1 = (double) lminp;

                                    }
                            }else{
                                     pdm++;
                                 }
                                } else {
                                    pdm++;
                                    e24 = 1;
                                    maxp1 = (double) maxp;
                                    minp1 = (double) minp;

                                }
                            } else {
                                pdm++;
                                e25 = 1;
                            }
                        } else {
                            pdm++;
                            e26 = 1;

                        }
                    } else if (pB == 1) {
                        if ((200 < pm - ps) && (pm - ps < 1000)) {
                            if ((20 < Math.abs(maxp - minp)) && (Math.abs(maxp - minp) < 80)) {
                                if ((20 < Math.abs(lmaxp - lminp)) && Math.abs(lmaxp - lminp) < 80) {
                                    // if(0<=Math.abs(lmaxp-maxp)&&Math.abs(lmaxp-maxp)<= 5){
                                    if ((200 < lpe - pe) && (lpe - pe < 1000)) {
                                        if ((200 < ps - psB) && (ps - psB < 1000)) {
                                            if (0 <= Math.abs(minp - minpB) && Math.abs(minp - minpB) <= 20) {
                                                numB = 1;
                                                e27 = 1;
                                            }
                                        }
                                        //if(0<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 5){
                                        pckl = 1;
                                     }else if(10<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 15){
                                         numL=1;
                                     }else{
                                         pdm++;
                                     }
                                    }else if(6<=Math.abs(lminp-minp)&&Math.abs(lminp-minp)<= 10){
                                        if ((200 < ps - psB) && (ps - psB < 1000)) {
                                            if (0 <= Math.abs(minp - minpB) && Math.abs(minp - minpB) <= 20) {
                                                numB = 1;
                                                e27 = 1;
                                            }
                                        }
                                        numL=1;
                                    }else{
                                        pdm++;
                                        e23 = 1;
                                        lmaxp1 = (double) lmaxp;
                                        lminp1 = (double) lminp;

                                    }
                            }else{
                                     pdm++;
                                 }
                                } else {
                                    pdm++;
                                    e24 = 1;
                                    maxp1 = (double) maxp;
                                    minp1 = (double) minp;

                                }
                            } else {
                                pdm++;
                                e25 = 1;
                            }
                        } else {
                            pdm++;
                            e26 = 1;

                        }
                    }

                    if (pckl == 1 && pdm == 0) {
                        if (ptotal_count == 0 || pd == 1) {
                            ptotal_count = ptotal_count + 4 + numB - numL;
                            pcount = pcount + 4 + numB - numL;


                            cc = 4 + numB - numL;
                            ptc = 1;

                            pB = 0;
                            numB = 0;
                            psB = 0;
                            minpB = 0;
                            numL = 0;

                            ps = 0;
                            ps = pe;
                            maxp = 0;
                            maxp = lmaxp;

                            pm = 0;
                            pm = lpe;
                            minp = 0;
                            minp = lminp;


                            pcn = 0;
                            pckl = 0;

                            pdm = 0;
                            pd = 0;

                            sTate=2;


                        } else if (ptotal_count > 0 && pd == 0) {
                            ptotal_count = ptotal_count + 2 - numL;
                            pcount = pcount + 2 - numL;

                            cc = 2 - numL;
                            ptc = 1;

                            pB = 0;
                            numB = 0;
                            psB = 0;
                            minpB = 0;
                            numL=0;

                            ps = 0;
                            ps = pe;
                            maxp = 0;
                            maxp = lmaxp;

                            pm = 0;
                            pm = lpe;
                            minp = 0;
                            minp = lminp;


                            pcn = 0;
                            pckl = 0;

                            pdm = 0;
                            pd = 0;

                            sTate=2;


                        }
                    } else {

                        pB = 0;
                        numB = 0;
                        psB = 0;
                        minpB = 0;
                        numL=0;

                        pc = 0;
                        ps = 0;
                        maxp = 0;

                        pk = 0;
                        pm = 0;
                        minp = 0;

                        pl = 0;
                        pe = 0;
                        lmaxp = 0;

                        pll = 0;
                        lpe = 0;
                        lminp = 0;

                        anp = -180;
                        pcn = 0;
                        pckl = 0;

                        pdm = 0;
                        pd = 0;
                        ptc = 0;
                        cc = 0;

                        sTate=0;

                    }
                    if (ptc == 1) {
                        ptc = 0;

                        pl = 0;
                        pe = 0;
                        lmaxp = 0;

                        pll = 0;
                        lpe = 0;
                        lminp = 0;

                        double rad = Math.toRadians(anglex + 30);

                        averx = 60 * sin(rad);


                        Log.d(Tag, " " + averx);
                        avery = 60 * cos(rad);


                        plc = 1;
                        if ((averx > 4 || avery > 4 || averx < -4 || avery < -4) && plc == 1) {
                            plc = 0;
                        averageX = averageX / 4;
                            averx = cc * averx / 4;
                            XXX = XXX + (int) averx;

                        averageY = averageY / 4;
                            avery = cc * avery / 4;
                            YYY = YYY + (int) avery;
                            wvLayout0401v3.loadUrl("javascript:androidBridge (" + XXX + ", " + YYY + ",'#000000')");
                            cc = 0;
                        }
                    }
                }
                tView.setText(String.valueOf(ptotal_count));
                tView1.setText(String.valueOf(total_count+ptotal_count));
            }*/


        }

        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                    /*   requestPermissions(this,);*/

                                       String Path = Environment.getExternalStorageDirectory().getPath();
                                      /* String FAX = "AX.txt";
                                       String FAY = "AY.txt";
                                       String FAZ = "AZ.txt";*/
                                       String CCur = "cur.txt";
                                       /*String Fre = "ees.txt";
                                       String FEC="EC.txt";
                                       String FEC1="EC1.txt";*/

                                       /*String Mfre = "mms.txt";*/
                                       /*String ST = "ST.txt";*/
                                       /*String M = "MM.txt";
                                       String ED = "EE.txt";*/
                                       /*String RRQY="RQY.txt";*/
                                       String AN3="QY.txt";
                                       String AN4="QP.txt";
                                       String AN5="QR.txt";
//                                       String TE="TIE.txt";



                                      /* File aaX = new File(Path, FAX);
                                       File aaY = new File(Path, FAY);
                                       File aaZ = new File(Path, FAZ);*/

                                       File ccC = new File(Path, CCur);

                                       /*File sS = new File(Path, Fre);
                                       File tA = new File(Path, FEC);
                                       File tAA = new File(Path, FEC1);*/
                                      /* File mM = new File(Path, Mfre);
*/
                                      /* File SA = new File(Path, ST);
*/

                                       File QY= new File(Path,AN3);
                                       File QP= new File(Path,AN4);
                                       File QR= new File(Path,AN5);
                                      /* File TIEE=new File(Path,TE);*/
                                     /*  File rq=new File(Path,RRQY);*/
                                     /*  File MM = new File(Path, M);
                                       File END = new File(Path, ED);*/

                                       try {
                                          /* OutputStream AAX = new FileOutputStream(aaX);
                                           AAX.write(AX.getBytes());


                                           OutputStream AAY = new FileOutputStream(aaY);
                                           AAY.write(AY.getBytes());


                                           OutputStream AAZ = new FileOutputStream(aaZ);
                                           AAZ.write(AZ.getBytes());*/


                                           OutputStream c = new FileOutputStream(ccC);
                                           c.write(UP.getBytes());

                                          /* OutputStream eE = new FileOutputStream(sS);
                                           eE.write(es.getBytes());

                                           OutputStream SE = new FileOutputStream(tA);
                                           SE.write(WSE.getBytes());//각도

                                           OutputStream SE1 = new FileOutputStream(tAA);
                                           SE1.write(WSE1.getBytes());*/

                                          /* OutputStream ES = new FileOutputStream(mM);
                                           ES.write(ms.getBytes());
*/




                                           OutputStream AG3 = new FileOutputStream(QY);
                                           AG3.write(QQY.getBytes());

                                           OutputStream AG4 = new FileOutputStream(QP);
                                           AG4.write(QQP.getBytes());

                                           OutputStream AG5 = new FileOutputStream(QR);
                                           AG5.write(QQR.getBytes());

                                           /*OutputStream TIE=new FileOutputStream(TIEE);
                                           TIE.write(ts.getBytes());*/

                                          /* OutputStream AG2=new FileOutputStream(rq);
                                           AG2.write(RQY.getBytes());*/



                                           /*OutputStream MD = new FileOutputStream(MM);
                                           MD.write(MMM.getBytes());

                                           OutputStream EEN = new FileOutputStream(END);
                                           EEN.write(EEE.getBytes());*/
                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }

                                   }
                               }

        );







    }
    @Override
    public void onAccuracyChanged (Sensor sensor,int accuracy){
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.resetBtn:
                total_count = 0;
                X = null;
                Y = null;
                Z = null;
                AX = null;
                AY = null;
                AZ = null;
                UP=  null;
                QQY= null;
                QQP= null;
                QQR= null;
                RQY=null;

                angle2=0;
                angle3=0;
                angle4=0;
                angle5=0;



                c=0;
                maxiaz=0;
                start=0;
                de=0;
                det=0;
                cd=0;
                cdd=0;
                cddd=0;
                accpzd=0;

                k=0;
                minaz=0;
                mid=0;
                dete=0;
                detec=0;
                cyd=0;
                cydd=0;
                cyddd=0;
                accpzdd=0;

                l=0;
                lmaxiaz=0;
                end=0;
                detect=0;
                tdetect=0;
                cyyd=0;
                cyydd=0;
                cyyddd=0;
                accpzddd=0;
                accp=0;

                d=0;

                XXX=0;
                YYY=0;

                input=0;

                ss=0;
                mm=0;
                ee=0;
                lee=0;

                ptotal_count=0;
                pcount=0;

                anp1=180;
                pB=0;
                numBp=0;
                psB=0;
                minpB=0;

                ps=0;
                pc=0;
                maxp=0;

                pm=0;
                pk=0;
                minp=0;

                pe=0;
                pl=0;
                lmaxp=0;

                lpe=0;
                pll=0;
                lminp=0;

                anp=-180;
                pcn=0;
                pckl=0;

                pdm=0;
                pd=0;
                ptc=0;
                cc=0;

                sTate=0;

                tView.setText(String.valueOf(ptotal_count+" "+total_count));
                break;


        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {


        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
            Toast.makeText(getApplication(),"되돌아가기 버튼이 클릭됨",Toast.LENGTH_SHORT).show();
        }
        if(event.getKeyCode()==KeyEvent.KEYCODE_MENU){
            if(event.isLongPress()){
                return true;
            }
            if(event.getKeyCode()!=0){
                return true;
            }

            Toast.makeText(getApplication(),"메뉴 버튼이 클릭됨",Toast.LENGTH_SHORT).show();
        }
        return super.dispatchKeyEvent(event);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      /*  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
        return true;
    }

    /**************TEST OTHERS CODE END************/
}

