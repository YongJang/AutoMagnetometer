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
                        + dataArrayList.get(i).getAbs() + "," + dataArrayList.get(i).getPosX() + "," + dataArrayList.get(i).getPosY() + "\r\n";
                os.write(data.getBytes());
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
