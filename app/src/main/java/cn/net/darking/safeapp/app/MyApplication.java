package cn.net.darking.safeapp.app;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
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

    TelephonyManager tm;

    @Override
    public void onCreate() {
        super.onCreate();
        //阿里推送初始化
        initCloudChannel(this);
        context = getApplicationContext();
        mOsVersion = getAndroidOSVersion();

        //获取手机号码
        tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();//获取智能设备唯一编号
        String tel = tm.getLine1Number();//获取本机号码
        String imei = tm.getSimSerialNumber();//获得SIM卡的序号
        String imsi = tm.getSubscriberId();//得到用户Id

        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.bindAccount(tel, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e("绑定账号", "==============初始化绑定成功!!!===============" + s);
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.e("绑定账号", "==============初始化绑定失败!!!===============" + s + "---原因---" + s1);
            }
        });
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
