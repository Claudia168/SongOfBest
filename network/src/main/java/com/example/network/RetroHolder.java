package com.example.network;

public class RetroHolder {

    private String baseUrl;

    private String apiKey;   //apikey

    private String kw;    //关键字

    private String artistId;   //歌星id

    private String musidId;  //歌曲id

    private String musicFileId;  //fileOp.id

    private String pageToken;  //代表当前页

    public RetroHolder(String baseUrl,String apiKey, String kw, String artistId,
                       String musidId, String musicFileId,String pageToken) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.kw = kw;
        this.artistId = artistId;
        this.musidId = musidId;
        this.musicFileId = musicFileId;
        this.pageToken = pageToken;
    }

    public RetroHolder() {
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getMusidId() {
        return musidId;
    }

    public void setMusidId(String musidId) {
        this.musidId = musidId;
    }

    public String getMusicFileId() {
        return musicFileId;
    }

    public void setMusicFileId(String musicFileId) {
        this.musicFileId = musicFileId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }
}
