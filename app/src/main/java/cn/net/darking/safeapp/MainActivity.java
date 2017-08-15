package cn.net.darking.safeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.net.darking.safeapp.mail.MailSender;
import cn.net.darking.safeapp.mail.MailSenderInfo;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.main_iv_main)
    ImageView iv_main;


    @BindView(R2.id.main_ll_safe)
    LinearLayout ll_safe;
    @BindView(R2.id.main_iv_safe)
    ImageView iv_safe;
    @BindView(R2.id.main_tv_safe)
    TextView tv_safe;

    @BindView(R2.id.main_iv_util)
    ImageView iv_util;
    @BindView(R2.id.main_tv_util)
    TextView tv_util;

    @BindView(R2.id.main_iv_service)
    ImageView iv_service;
    @BindView(R2.id.main_tv_service)
    TextView tv_service;

    @BindView(R2.id.main_iv_mine)
    ImageView iv_mine;
    @BindView(R2.id.main_tv_mine)
    TextView tv_mine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        onClick(ll_safe);
    }


    private void setDefault() {
        iv_safe.setImageResource(R.mipmap.safe_n);
        iv_util.setImageResource(R.mipmap.util_n);
        iv_service.setImageResource(R.mipmap.service_n);
        iv_mine.setImageResource(R.mipmap.mine_n);

        tv_safe.setTextColor(getResources().getColor(R.color.app_gray));
        tv_util.setTextColor(getResources().getColor(R.color.app_gray));
        tv_service.setTextColor(getResources().getColor(R.color.app_gray));
        tv_mine.setTextColor(getResources().getColor(R.color.app_gray));
    }


    @OnClick({R.id.main_ll_safe, R.id.main_ll_util, R.id.main_ll_service, R.id.main_ll_mine, R.id.main_iv_main})
    void onClick(View view) {

        switch (view.getId()) {

            case R.id.main_ll_safe:
                setDefault();
                iv_main.setImageResource(R.mipmap.main_safe);

                iv_safe.setImageResource(R.mipmap.safe_s);
                tv_safe.setTextColor(getResources().getColor(R.color.app_green));
                break;

            case R.id.main_ll_util:
                setDefault();
                iv_main.setImageResource(R.mipmap.main_util);
                iv_util.setImageResource(R.mipmap.util_s);
                tv_util.setTextColor(getResources().getColor(R.color.app_green));
                break;

            case R.id.main_ll_service:
                setDefault();
                iv_service.setImageResource(R.mipmap.service_s);
                tv_service.setTextColor(getResources().getColor(R.color.app_green));
                break;

            case R.id.main_ll_mine:
                setDefault();
                iv_main.setImageResource(R.mipmap.main_mine);
                iv_mine.setImageResource(R.mipmap.mine_s);
                tv_mine.setTextColor(getResources().getColor(R.color.app_green));
                break;

            case R.id.main_iv_main:
                Toast.makeText(MainActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
                send();

                break;
        }
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
