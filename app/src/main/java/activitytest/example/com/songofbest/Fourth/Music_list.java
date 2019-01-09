package activitytest.example.com.songofbest.Fourth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import activitytest.example.com.songofbest.R;

public class Music_list extends BaseAdapter {

    private List<MusicBean> musicNames;
    private Context mContext;
    private boolean isplay;
    private PlayListener mPlayCall;
    private int p = -1;

    public void setPlayListener(PlayListener playListener){
        this.mPlayCall = playListener;
    }

    public Music_list(List<MusicBean> names,Context context){
        this.mContext = context;
        this.musicNames = names;
    }

    @Override
    public int getCount() {
        return musicNames.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;
        final MusicBean musicBean = musicNames.get(position);
        boolean isplay = musicBean.isIsplay();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.music_layout,parent,false);
        final ImageView imageView = rootView.findViewById(R.id.music_play);
        if(isplay){
            imageView.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
        }else{
            imageView.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
        }
        TextView textView = rootView.findViewById(R.id.music_name);
        textView.setText(musicBean.getMusic_name());
        imageView.setTag(isplay);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(Boolean)imageView.getTag()){
                    if(mPlayCall!=null){
                        mPlayCall.play(musicBean.getPosition());
                        imageView.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                        imageView.setTag(true);
                        musicBean.setIsplay(true);
                    }
                }else{
                    if(mPlayCall!=null){
                        mPlayCall.stop(musicBean.getPosition());
                        imageView.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                        imageView.setTag(false);
                        musicBean.setIsplay(false);
                    }
                }
            }
        });
        return rootView;
    }

    public interface PlayListener{
        void play(int position);
        void stop(int position);
    }
}
