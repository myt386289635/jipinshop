package com.example.administrator.jipinshop.netwrok;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.auto.ApplicationScope;
import com.example.administrator.jipinshop.jpush.huawei.MyHmsMessageService;
import com.example.administrator.jipinshop.jpush.meizu.MyPushMsgReceiver;

import dagger.Component;

@ApplicationScope
@Component(modules = {
        RetrofitModule.class,
        OKHttpModule.class,
        ApplicationModule.class})
public interface ApplicationComponent {
   void inject(MyApplication application);
   void inject(MyHmsMessageService service);
   void inject(MyPushMsgReceiver receiver);
   //父亲Component里module的方法需要暴露出来才能提供给子类Component使用
   APIService apiService();
}
