package com.example.network.bean;

import java.util.List;

public class QQMusicBean {


    /**
     * hasNext : false
     * appCode : qqmusic
     * pageToken : null
     * data : ["http://dl.stream.qqmusic.qq.com/C400002kkJSE0IeS1F.m4a?vkey=3C76AB8E3003F77D4251B9021A2E3897238858E460BD600D5476859F82275CF77282E909FDAD2D1B97E70C9ECFB40FEA84DFAE8D54FF481B&guid=2889959152&uin=0&fromtag=66"]
     * dataType : music
     * retcode : 000000
     */

    private boolean hasNext;
    private String appCode;
    private Object pageToken;
    private String dataType;
    private String retcode;
    private List<String> data;  //歌曲的url

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Object getPageToken() {
        return pageToken;
    }

    public void setPageToken(Object pageToken) {
        this.pageToken = pageToken;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
