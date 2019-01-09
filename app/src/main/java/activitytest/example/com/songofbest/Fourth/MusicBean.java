package activitytest.example.com.songofbest.Fourth;

public class MusicBean {

    private int position;

    private boolean isplay;

    private String music_name;

    public MusicBean(int position, boolean isplay, String music_name) {
        this.position = position;
        this.isplay = isplay;
        this.music_name = music_name;
    }

    public MusicBean() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isIsplay() {
        return isplay;
    }

    public void setIsplay(boolean isplay) {
        this.isplay = isplay;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }
}
