package com.zzy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.zzy.aidl.IUserManager;
import com.zzy.aidl.User;

import java.util.ArrayList;
import java.util.List;

public class ServiceT extends Service {
    private final String TAG = this.getClass().getSimpleName();

    private List<User> mUsers = new ArrayList<>();

    private final IUserManager.Stub mUserManager = new IUserManager.Stub() {
        @Override
        public List<User> getUsers() {
            return mUsers;
        }

        @Override
        public void setUser(User user) {
            synchronized (this) {
                if (user == null) {
                    return;
                }
                Log.d(TAG, user.getName());
                // 修改属性，看客户端数据变化
                user.setName("第二行代码");
                Log.d(TAG, user.getName());
                if (!mUsers.contains(user)) {
                    mUsers.add(user);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, String.format("service on bind,intent = %s", intent.toString()));
        return mUserManager;
    }
}
