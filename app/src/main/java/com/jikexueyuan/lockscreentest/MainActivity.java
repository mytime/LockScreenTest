package com.jikexueyuan.lockscreentest;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 一键锁屏
 * -----------------------
 * MainActivity: 配置
 * LockScreen: 锁屏
 * MyReceiver: 设备管理接收器
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //定义对象
    private Button btnAdmin;
    private Button btnCancel;
    private Button btnLock;
    private DevicePolicyManager devicePolicyManager;
    private static final int DEVICE_POLICY_MANAGER_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        init();
        //权限检查
        getAdminState();
    }

    /**
     * 检查权限状态
     * 1 如果已经获取管理权限，隐藏btnAdmin
     */
    private void getAdminState() {
        if (devicePolicyManager.isAdminActive(new ComponentName(this,MyReceiver.class))){
            pageSetAdmin();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        //实例
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        btnAdmin = (Button) findViewById(R.id.btnAdmin);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnLock = (Button) findViewById(R.id.btnLock);

        //按钮监听
        btnAdmin.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnLock.setOnClickListener(this);
    }

    /**
     * 设置界面
     */
    private void pageSetAdmin() {
        btnAdmin.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.VISIBLE);
        btnLock.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdmin:
                //获取权限
                getAdmin();
                break;
            case R.id.btnCancel:
                //取消权限
                devicePolicyManager.removeActiveAdmin(new ComponentName(this,
                        MyReceiver.class));
                pageLock();
                break;
            case R.id.btnLock:
                //锁屏
                devicePolicyManager.lockNow();
                break;
        }
    }

    /**
     * 获取锁屏权限
     */
    private void getAdmin() {
        Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                new ComponentName(this, MyReceiver.class));
        startActivityForResult(i,DEVICE_POLICY_MANAGER_REQUEST_CODE);
    }

    /**
     * 取消权限后界面变化
     */
    private void pageLock() {
        btnAdmin.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
        btnLock.setVisibility(View.INVISIBLE);
    }

    /**
     * Intent返回结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case RESULT_OK:
                pageSetAdmin();
                break;
            case RESULT_CANCELED:
                break;
        }
    }
}
