package com.example.network;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.network.bean.QQMusicBean;
import com.example.network.bean.QQMusicDetailsBean;
import com.example.network.bean.QQMusicSearcherBean;
import com.example.network.listener.RequestDetailsCallBack;
import com.example.network.listener.RequestPlayCallBack;
import com.example.network.listener.RequestSearcherCallBack;


import java.io.IOException;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static final String BASE_QQ_URL = "http://api01.idataapi.cn:8000/";
    public static final String APIKEY = "u1O0btEhc66nvLfbp7BqV6yFVHL8ajFXcg1VyYrxnJgUoXa987MjOO6Q65de5d7P";

    public void playMusic(RetroHolder holder, final RequestPlayCallBack callback){
        String musicId = holder.getMusicFileId();
        if(musicId==null) return;
        String baseUrl = getBaseUrl(holder);
        String apiKey = getApiKey(holder);
        GetServer server = getServer(baseUrl);
        server.getQQMusic(musicId,apiKey).enqueue(new Callback<QQMusicBean>() {
            @Override
            public void onResponse(Call<QQMusicBean> call, Response<QQMusicBean> response) {
                if(response.body()!=null){
                    callback.play(response.body().getData().get(0));
                }else{
                    callback.error(response.message());
                }
            }

            @Override
            public void onFailure(Call<QQMusicBean> call, Throwable t) {
                callback.failure(t.getMessage());
            }
        });
    }

    public void Searcher(RetroHolder holder, final RequestSearcherCallBack callBack){
        String kw = holder.getKw();
        if(kw==null || kw.isEmpty()) return;
        String pageToken = holder.getPageToken();
        if(pageToken==null || pageToken.isEmpty()){
            pageToken = "1";
        }
        String baseUrl = getBaseUrl(holder);
        String apiKey = getApiKey(holder);
        GetServer server = getServer(baseUrl);
        server.getQQSearcher(kw,pageToken,apiKey).enqueue(new Callback<QQMusicSearcherBean>() {
            @Override
            public void onResponse(Call<QQMusicSearcherBean> call, Response<QQMusicSearcherBean> response) {
                QQMusicSearcherBean qq = response.body();
                if(qq!=null)
                    callBack.RequestSuccess(qq);
                else
                    callBack.RequestError(response.message());
            }

            @Override
            public void onFailure(Call<QQMusicSearcherBean> call, Throwable t) {
                callBack.RequestFail(t.getMessage());
            }
        });
    }

    public void getMusicDetails(RetroHolder holder, final RequestDetailsCallBack callBack){
        String musicId = holder.getMusidId();
        if(musicId==null || musicId.isEmpty()) return;
        String baseUrl = getBaseUrl(holder);
        String apiKey = getApiKey(holder);
        GetServer server = getServer(baseUrl);
        server.getQQMusicDetails(musicId,apiKey).enqueue(new Callback<QQMusicDetailsBean>() {
            @Override
            public void onResponse(Call<QQMusicDetailsBean> call, Response<QQMusicDetailsBean> response) {
                QQMusicDetailsBean qq = response.body();
                if(qq!=null)
                    callBack.RequestSuccess(qq);
                else
                    callBack.RequestError(response.message());
            }

            @Override
            public void onFailure(Call<QQMusicDetailsBean> call, Throwable t) {
                callBack.RequestFail(t.getMessage());
            }
        });
    }

    private String getApiKey(RetroHolder holder){
        String apiKey = holder.getApiKey();
        if(apiKey==null || apiKey.isEmpty()){
            apiKey = APIKEY;
        }
        return apiKey;
    }

    private String getBaseUrl(RetroHolder holder){
        String baseUrl = holder.getBaseUrl();
        if(baseUrl==null || baseUrl.isEmpty()){
            baseUrl = BASE_QQ_URL;
        }
        return baseUrl;
    }

    private GetServer getServer(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GetServer.class);
    }
}
