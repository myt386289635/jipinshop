package com.example.administrator.jipinshop.jpush.huawei;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;
import com.example.administrator.jipinshop.netwrok.ApplicationModule;
import com.example.administrator.jipinshop.netwrok.DaggerApplicationComponent;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/7/23
 * @Describe 华为、小米、vivo、魅族推送中转activity
 */
public class HmsActivity extends AppCompatActivity {

    @Inject
    AppStatisticalUtil mStatisticalUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx);
        ApplicationComponent mApplicationComponent =
                DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                        .build();
        mApplicationComponent.inject(this);
        Intent intent = getIntent();
        if (intent != null){
            String targetType = intent.getStringExtra("targetType");
            String targetId = intent.getStringExtra("targetId");
            String targetTitle = intent.getStringExtra("targetTitle");
            String source = intent.getStringExtra("source");
            String remark = intent.getStringExtra("remark");
            String jpcMsgId = intent.getStringExtra("jpcMsgId");
            mStatisticalUtil.addEvent("push_click." + jpcMsgId);
            if (isExistMainActivity()) {//是否已经启动MainActivity
                //跳转到具体页面的代码
                ShopJumpUtil.openBanner(this, targetType, targetId, targetTitle,source,remark);
            } else {
                //启动APP的代码
                startActivity(new Intent(this, MainActivity.class)
                        .putExtra("targetType",targetType)
                        .putExtra("target_id" , targetId)
                        .putExtra("target_title" , targetTitle)
                        .putExtra("source" , source)
                        .putExtra("remark", remark)
                        .putExtra("isAd",true)//从广告页点击过来的
                );
            }
            finish();
        }
    }

    private boolean isExistMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) {// 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);//获取从栈顶开始往下查找的10个activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) {// 说明它已经启动了
                    flag = true;
                    break;//跳出循环，优化效率
                }
            }
        }
        return flag;//true 存在 falese 不存在
    }
}
