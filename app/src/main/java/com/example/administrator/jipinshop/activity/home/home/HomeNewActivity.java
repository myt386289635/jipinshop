package com.example.administrator.jipinshop.activity.home.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.fragment.evaluationkt.EvaluationNewFragment;
import com.example.administrator.jipinshop.fragment.home.HomeNewFragment;
import com.example.administrator.jipinshop.fragment.index.IndexVideoFragment;
import com.example.administrator.jipinshop.fragment.member.MemberFragment;
import com.example.administrator.jipinshop.fragment.school.KTSchoolFragment;

/**
 * @author 莫小婷
 * @create 2020/4/1
 * @Describe fragment变为activity的中转站
 */
public class HomeNewActivity extends BaseActivity {

    public static final int bangdan = 1;//榜单
    public static final int member = 2;//会员
    public static final int evaluation =3;//评测
    public static final int indexVideo = 4;//引导页的视频
    public static final int school = 5; //省钱教程

    // 定义
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction =  supportFragmentManager.beginTransaction();
        int type = getIntent().getIntExtra("type",bangdan);
        if (type == bangdan){
            fragmentTransaction.add(R.id.home_fragment, new HomeNewFragment()).commit();
        }else if (type == member){
            mImmersionBar.reset()
                    .transparentStatusBar()
                    .statusBarDarkFont(false, 0f)
                    .init();
            fragmentTransaction.add(R.id.home_fragment, MemberFragment.getInstance("2",
                    getIntent().getBooleanExtra("isyear",false))).commit();
        }else if (type == evaluation){
            fragmentTransaction.add(R.id.home_fragment, EvaluationNewFragment.getInstance("2")).commit();
        }else if (type == indexVideo){
            fragmentTransaction.add(R.id.home_fragment, IndexVideoFragment.getInstence()).commit();
        }else if (type == school){
            fragmentTransaction.add(R.id.home_fragment, KTSchoolFragment.getInstance()).commit();
        }
    }
}
