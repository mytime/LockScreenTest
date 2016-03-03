package com.jikexueyuan.lockscreentest;

import android.app.admin.DeviceAdminReceiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 设备管理接收器
 */
public class MyReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        mToast(context,"已获取锁屏权限");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        mToast(context,"已取消锁屏权限");
    }

    /**
     * Toast消息
     * @param context
     * @param s
     */
    private void mToast(Context context,String s) {
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
    }
}
