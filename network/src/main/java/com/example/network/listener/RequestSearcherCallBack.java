package com.example.network.listener;

import com.example.network.bean.QQMusicSearcherBean;

public interface RequestSearcherCallBack {

    void RequestSuccess(QQMusicSearcherBean qqMusicSearcherBean);

    void RequestError(String message);

    void RequestFail(String message);
}
