package cn.net.darking.safeapp.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import java.io.Serializable;


/**
 * Created by jnn on 2017/1/3.
 */
public class MyApplication extends Application implements Serializable {


    public Context context;

//    private HashMap<String, Integer> mPushNum;

    public int mOsVersion = 0;
    public int mCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        //阿里推送初始化
        initCloudChannel(this);
        context = getApplicationContext();
        mOsVersion = getAndroidOSVersion();
        mCount++;
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();


        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.e("阿里推送", "==============初始化推送成功init cloudchannel success===============");
                //cloudPushService.getDeviceId();
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e("阿里推送", "===========初始化推送失败init cloudchannel failed -- errorcode:========" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }


    public static int getAndroidOSVersion() {
        int osVersion;
        try {
            osVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            osVersion = 0;
        }

        return osVersion;
    }


}
