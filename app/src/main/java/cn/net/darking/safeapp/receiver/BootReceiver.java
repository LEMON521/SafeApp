package cn.net.darking.safeapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Zrzc on 2017/8/16.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {     // boot
            Log.e("BootReceiver","开机启动了!!!!!!!");
        }
    }
}
