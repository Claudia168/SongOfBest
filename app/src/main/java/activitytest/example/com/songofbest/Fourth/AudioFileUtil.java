package activitytest.example.com.songofbest.Fourth;

import android.os.Environment;

import java.io.File;

public class AudioFileUtil {

    /**
     * 判断是否有外部存储设备sdcard
     *
     * @return true | false
     */
    private static boolean isSdcardExit() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    private static String getFileBasePath(){
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BlueTest/recordFile";
        File filePath = new File(fileBasePath);
        if (!filePath.exists()) {
            try {
                //按照指定的路径创建文件夹
                filePath.mkdirs();
            } catch (Exception e) { }
        }
        return fileBasePath;
    }

    public static String getRawFile(String path){
        if(isSdcardExit()){
            return getFileBasePath()+"/"+path;
        }
        return "";
    }
}
