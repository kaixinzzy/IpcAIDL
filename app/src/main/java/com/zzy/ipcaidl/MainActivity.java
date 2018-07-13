package com.zzy.ipcaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zzy.aidl.IUserManager;
import com.zzy.aidl.User;

import java.util.List;

/**
 * Android Interface Definition Language - Android接口定义语言
 *  IPC跨进程通信
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();

    private boolean isConnect = false;// 当前连接状态
    private IUserManager iUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isConnect) {
            connectService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isConnect) {
            unbindService(mServiceConnection);
        }
    }

    // 连接Service
    private void connectService() {
        Intent intent = new Intent();
        intent.setAction("com.zzy.aidl");
        intent.setPackage("com.zzy.ipcaidl");// 设置应用包名，不然android5.0以后隐式启动服务报异常
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "service connected");
            isConnect = true;
            iUserManager = IUserManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "service disconnected");
            isConnect = false;
        }
    };
    private User user = null;
    @Override
    public void onClick(View view) {
        if (!isConnect) {
            connectService();
            return;
        }
        switch (view.getId()) {
            case R.id.set:
                user = new User();
                user.setName("第一行代码");
                try {
                    iUserManager.setUser(user);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.get:
                try {
                    List<User> users = iUserManager.getUsers();
                    for (User u : users) {
                        Log.d(TAG, u.getName());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.log:
                Log.d(TAG, user.getName());
                break;
        }
    }
}
