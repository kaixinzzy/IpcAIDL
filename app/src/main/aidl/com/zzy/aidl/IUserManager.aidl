package com.zzy.aidl;

// 导入所有需要使用非基础数据类型的包
import com.zzy.aidl.User;
//import com.zzy.aidl.MainListener;
import com.zzy.aidl.IMainListener;

interface IUserManager {

    //所有返回值前都不需要加任何东西，不管什么数据类型
    List<User> getUsers();
//    User getUser();

    //传参时除了基本数据类型、String、CharSequence外，都需要在前面加上定向tag，具体加什么量需而定
    // 定向tag in：只允许Client将对象A传递给Service，当修改Service的A时，Client中的A不会发生改变
//    void setUser(in User user);
    // 定向tag out: Client传递空对象A给Service，当修改Service的A时，Client的A也会随之改变
    void setUser(out User user);
    // 定向tag inout：双向车道
//    void setUser(inout User user);
//    void setUserName(String name);

    void registerListener(in IMainListener listener);// 注册回调接口
    void unregisterListener(in IMainListener listener);// 解注册回调接口

}
