package com.example.network.listener;

public interface RequestPlayCallBack {

    void play(String url);

    void error(String message);

    void failure(String message);
}
