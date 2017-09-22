package io.github.yongjang.automagnetometer;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongJang on 2017-08-02.
 */

public class FileManager {
    String PATH = Environment.getExternalStorageDirectory().getPath();
    File magnetometerResultFile;
    FileOutputStream os;
    private float ratioXForPDR = 2.78f;   // MagneticMeasurementViewWithPDR class 에서 PDR을 이용해서 자기장을 측정할 시 사용되는 hanasquare 맵과 현재 맵의 배율이 4.5배 차이남
    private float ratioYForPDR = 2.86f;   // 해당 클래스에서 사용하는 hanasquare 맵의 position 좌표가 (100, 200)일 때 같은 위치를 가르키는 PDR 맵의 좌표는(450, 900)으로 나옴 두 데이터를 비교하기 위해 posX posY에만 배율을 맞춰줌

    public FileManager() {  }

    public void initialResultFile(String fileName) {
        this.magnetometerResultFile = new File(PATH, fileName);
    }

    public void writeDataList(List<MagData> dataArrayList) {
        try {
            os = new FileOutputStream(magnetometerResultFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            os.write("#X,#Y,#Z,#abs,#PosX,#PosY\r\n".getBytes());
            for (int i = 0; i < dataArrayList.size(); i++) {
                String data = dataArrayList.get(i).getX() + "," + dataArrayList.get(i).getY() + "," + dataArrayList.get(i).getZ() + ","
                        + dataArrayList.get(i).getAbs() + "," + (dataArrayList.get(i).getPosX() * ratioXForPDR) + "," + (dataArrayList.get(i).getPosY() * ratioYForPDR) + "\r\n";
                os.write(data.getBytes());
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
