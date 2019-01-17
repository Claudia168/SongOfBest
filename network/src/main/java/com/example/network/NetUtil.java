package com.example.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetUtil {

    public static final String NET_STATU_FAILE = "无网络连接";
    public static final String NET_STATE_WIFI = "WIFI";
    public static final String NET_STATE_2G = "2g";
    public static final String NET_STATE_3G = "3g";
    public static final String NET_STATE_4G = "4g";

    private static String type = "";

    public static String getNetType(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null){
            type = NET_STATU_FAILE;
        }else if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
            type = NET_STATE_WIFI;
        }else if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
            int subType = networkInfo.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA
                    || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = NET_STATE_2G;
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                    || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = NET_STATE_3G;
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = NET_STATE_4G;
            }
        }
        return type;
    }
}
