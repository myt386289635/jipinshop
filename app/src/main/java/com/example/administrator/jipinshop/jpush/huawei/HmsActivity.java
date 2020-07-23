package com.example.administrator.jipinshop.jpush.huawei;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.wellcome.AdActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.util.ShopJumpUtil;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/7/23
 * @Describe 中转activity
 */
public class HmsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx);
        Intent intent = getIntent();
        if (intent != null){
            String targetType = intent.getStringExtra("targetType");
            String targetId = intent.getStringExtra("targetId");
            String targetTitle = intent.getStringExtra("targetTitle");
            String source = intent.getStringExtra("source");
            if (isExistMainActivity()) {//是否已经启动MainActivity
                //跳转到具体页面的代码
                ShopJumpUtil.openBanner(this, targetType, targetId, targetTitle,source);
            } else {
                //启动APP的代码
                startActivity(new Intent(this, MainActivity.class)
                        .putExtra("targetType",targetType)
                        .putExtra("target_id" , targetId)
                        .putExtra("target_title" , targetTitle)
                        .putExtra("source" , source)
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
