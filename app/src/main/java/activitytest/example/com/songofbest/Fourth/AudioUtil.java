package activitytest.example.com.songofbest.Fourth;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.io.FileOutputStream;

public class AudioUtil {

    //音频来源：麦克风
    public static final int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;

    /*采样率：音频的采样频率，每秒钟能够采样的次数，采样率越高，音质越高。
    给出的实例是44100、22050、11025但不限于这几个参数。
    例如要采集低质量的音频就可以使用4000、8000等低采样率。
    */
    public static final int AUDIO_SAMPLE_RATE = 11025;

    //声道设置：android支持双声道立体声和单声道。MONO单声道，STEREO立体声
    public static final int AUDIO_CHANNEL_CONFIG = AudioFormat.CHANNEL_CONFIGURATION_MONO;

    //编码制式和采样大小：采集来的数据当然使用PCM编码
    // 脉冲代码调制编码，即PCM编码。PCM通过抽样、量化、编码三个步骤将连续变化的模拟信号转换为数字编码。
    // android支持的采样大小16bit 或者8bit。
    // 当然采样大小越大，那么信息量越多，音质也越高，
    // 现在主流的采样大小都是16bit，在低质量的语音传输的时候8bit足够了。
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_8BIT;

    private AudioRecord mRecord;
    private int bufferSizeInBytes = 0; // 缓冲区大小
    private String rawFilePath; //原始数据存放路径
    private boolean isRecord ; //是否正在录制
    private AudioTrackUtil mAudioTrack;
    private SendData mSendData;

    public void setmSendData(SendData mSendData) {
        this.mSendData = mSendData;
    }

    private AudioUtil(){
        mAudioTrack = AudioTrackUtil.getInstance();
    }

    public void playVolu(int type){
        if(type==1){
            mAudioTrack.playleft();
        }else if(type==2){
            mAudioTrack.playright();
        }else if(type==3){
            mAudioTrack.playLiti();
        }
    }

    private static final class HOLDER{
        private static final AudioUtil INSTANCE = new AudioUtil();
    }

    public String getPath(){
        return rawFilePath;
    }

    public static AudioUtil getInstance(){
        return HOLDER.INSTANCE;
    }

    public boolean isRecord() {
        return isRecord;
    }

    public int getId(){
        if(mAudioTrack!=null){
            return mAudioTrack.getId();
        }
        return -1;
    }

    private void createAudio(){
        rawFilePath = AudioFileUtil.getRawFile(System.currentTimeMillis()+".raw");
        bufferSizeInBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE,AUDIO_CHANNEL_CONFIG,AUDIO_FORMAT);
        mRecord = new AudioRecord(AUDIO_INPUT,AUDIO_SAMPLE_RATE,AUDIO_CHANNEL_CONFIG,
                AUDIO_FORMAT,bufferSizeInBytes);
    }

    public void startRecord(){
        if(!isRecord){
            if(mRecord!=null) {
                stop();
            }
            createAudio();

            mRecord.startRecording();
            isRecord = true;
            new AudioWriteThread().start();
        }
    }

    public void startsendRecord(){
        if(!isRecord){
            if(mRecord!=null) {
               stop();
            }
            createAudio();

            mRecord.startRecording();
            isRecord = true;
            new AudiosendThread().start();
        }
    }

    private class AudioWriteThread extends Thread{
        @Override
        public void run() {
            writeDataToFile();
        }
    }

    private class AudiosendThread extends Thread{
        @Override
        public void run() {
            sendPlay();
        }
    }

    public void stop(){
        if(mRecord!=null && isRecord){
            mRecord.stop();
            mRecord.release();
            isRecord = false;
            mRecord = null;
            mAudioTrack.stop();
        }
    }

    private void writeDataToFile(){
        byte[] audioData = new byte[bufferSizeInBytes];
        FileOutputStream out = null;
        int readsize = 0;

        /*
        try {
            out = new FileOutputStream(new File(rawFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */

        while (isRecord){
            readsize = mRecord.read(audioData,0,audioData.length);
            if(AudioRecord.ERROR_INVALID_OPERATION!=readsize){
                /*
                if(mSendData!=null){
                    mSendData.sendDatas(audioData);
                }
                */
                mAudioTrack.play(audioData);
                /*
                try {
                    out.write(audioData,0,audioData.length);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
            }
        }
        /*
        if(out!=null){
            try {
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
    }

    private void sendPlay(){
        byte[] audioData = new byte[bufferSizeInBytes];
        int readsize = 0;

        /*
        try {
            out = new FileOutputStream(new File(rawFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */

        while (isRecord){
            readsize = mRecord.read(audioData,0,audioData.length);
            if(AudioRecord.ERROR_INVALID_OPERATION!=readsize){
                if(mSendData!=null){
                    mSendData.sendDatas(audioData);
                }

                //mAudioTrack.play(audioData);
                /*
                try {
                    out.write(audioData,0,audioData.length);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
            }
        }
        /*
        if(out!=null){
            try {
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
    }
}
