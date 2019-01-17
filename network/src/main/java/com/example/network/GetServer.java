package com.example.network;
import com.example.network.bean.QQMusicBean;
import com.example.network.bean.QQMusicDetailsBean;
import com.example.network.bean.QQMusicSearcherBean;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetServer {

    @GET("music/qqmusic")
    Call<QQMusicBean> getQQMusic(@Query("mid") String q, @Query("apikey") String apikey);

    @GET("music/qqmusic")

    Call<QQMusicSearcherBean> getQQSearcher(@Query("kw") String kw,@Query("pageToken") String pageToken,
                                            @Query("apikey") String apikey);

    @GET("music/qqmusic")
    Call<QQMusicDetailsBean> getQQMusicDetails(@Query("id") String id,@Query("apikey") String apikey);
}
