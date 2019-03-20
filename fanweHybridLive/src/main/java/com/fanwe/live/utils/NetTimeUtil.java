package com.fanwe.live.utils;

import android.provider.Settings;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.ServerData;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class NetTimeUtil {
    private NetTimeUtil(){}
    private long difftime;
    public static NetTimeUtil getInstance(){
        return  NetHoldler.instance;
    }
    private static class NetHoldler{
        private static final NetTimeUtil instance=new NetTimeUtil();
    }
    private long serverdata=0;
    public void caulateDiffTime(){
        CommonInterface.requestServerData(new AppRequestCallback<ServerData>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (null!=actModel.getServertime()) {
                    difftime=System.currentTimeMillis()-Long.parseLong(actModel.getServertime());
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
            }
        });
    }
    public long getServerData() {
        return System.currentTimeMillis()-difftime;
    }
}
