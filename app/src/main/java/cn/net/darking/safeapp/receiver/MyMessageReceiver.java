package cn.net.darking.safeapp.receiver;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.util.Map;

import cn.net.darking.safeapp.mail.MailSender;
import cn.net.darking.safeapp.mail.MailSenderInfo;

/**
 * Created by Zrzc on 2017/8/15.
 */

public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        send();

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

    private void send() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MailSenderInfo mailInfo = new MailSenderInfo();
                    mailInfo.setMailServerHost("smtp.163.com");
                    mailInfo.setMailServerPort("25");
                    mailInfo.setValidate(true);
                    mailInfo.setUserName("fsdz10086@163.com"); // 你的邮箱地址
//					mailInfo.setPassword("guolugui521");// 您的邮箱密码
                    mailInfo.setPassword("fsdz10086");// 这里用的是客户端授权码
                    mailInfo.setFromAddress("fsdz10086@163.com"); // 发送的邮箱
                    mailInfo.setToAddress("jsdz10086@163.com"); // 发到哪个邮件去
                    mailInfo.setSubject("测试发送"); // 邮件主题
                    mailInfo.setContent("地址为:朗廷大厦"); // 邮件文本
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

}
