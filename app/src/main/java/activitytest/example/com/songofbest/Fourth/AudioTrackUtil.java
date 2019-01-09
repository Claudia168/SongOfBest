package activitytest.example.com.songofbest.Fourth;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioTrackUtil {

    private AudioTrack mTrack;
    private boolean isPaly;
    private int bufferSize;
    private AudioAttributes attributes;
    private AudioFormat audioFormat;

    private AudioTrackUtil(){
        bufferSize = AudioTrack.getMinBufferSize(AudioUtil.AUDIO_SAMPLE_RATE,AudioUtil.AUDIO_CHANNEL_CONFIG,
                AudioUtil.AUDIO_FORMAT);
        attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        audioFormat = new AudioFormat.Builder()
                .setSampleRate(AudioUtil.AUDIO_SAMPLE_RATE)
                .setEncoding(AudioUtil.AUDIO_FORMAT)
                .setChannelMask(AudioUtil.AUDIO_CHANNEL_CONFIG)
                .build();
        create();
    }

    private static final class Holder{
        private static final AudioTrackUtil INSTANCE = new AudioTrackUtil();
    }

    public void playleft(){
        mTrack.setStereoVolume(AudioTrack.getMaxVolume(),0);
    }

    public void playright(){
        mTrack.setStereoVolume(0,AudioTrack.getMaxVolume());
    }

    public void playLiti(){
        mTrack.setStereoVolume(AudioTrack.getMaxVolume(),AudioTrack.getMaxVolume());
    }

    public static AudioTrackUtil getInstance(){
        return Holder.INSTANCE;
    }

    private void create(){
        int seeesionId = AudioManager.AUDIO_SESSION_ID_GENERATE;
        mTrack = new AudioTrack(attributes,audioFormat,bufferSize,AudioTrack.MODE_STREAM,seeesionId);
        //mTrack = new AudioTrack(AudioManager.STREAM_MUSIC,AudioUtil.AUDIO_SAMPLE_RATE,
                //AudioUtil.AUDIO_CHANNEL_CONFIG,AudioUtil.AUDIO_FORMAT,bufferSize,AudioTrack.MODE_STREAM);
    }

    public int getId(){
        if(mTrack!=null)
            return mTrack.getAudioSessionId();
        return -1;
    }

    public void play(final String path){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!isPaly) {
                    isPaly = true;
                    if (mTrack == null) {
                        create();
                    }
                }
                mTrack.play();
                FileInputStream inputStream = null;
                byte[] data = new byte[bufferSize];
                try {
                    inputStream = new FileInputStream(new File(path));
                    while (inputStream.available()>0){
                        int readCount = inputStream.read(data,0,data.length);
                        if(readCount == AudioTrack.ERROR_INVALID_OPERATION|| readCount == AudioTrack.ERROR_BAD_VALUE)
                            continue;
                        if(readCount!=-1 && readCount!=0)
                            mTrack.write(data,0,data.length);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(inputStream!=null){
                    try {
                        inputStream.close();
                        inputStream = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void play(final byte[] datas){
        if(mTrack==null) {
            create();
        }
        mTrack.play();
        mTrack.write(datas,0,datas.length);
    }

    public void stop(){
        if(mTrack!=null){
            isPaly = false;
            mTrack.stop();
            mTrack.release();
            mTrack = null;
        }
    }
}
