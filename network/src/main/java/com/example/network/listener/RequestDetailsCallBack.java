package com.example.network.listener;

import com.example.network.bean.QQMusicDetailsBean;

public interface RequestDetailsCallBack {

    void RequestSuccess(QQMusicDetailsBean qqMusicDetailsBean);

    void RequestError(String message);

    void RequestFail(String message);
}
