package com.example.administrator.jipinshop.activity.home;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewPeopleActivity;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeDetailActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.DaggerBaseActivityComponent;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeNewPeopleBus;
import com.example.administrator.jipinshop.bean.eventbus.TryStatusBus;
import com.example.administrator.jipinshop.fragment.evaluationkt.EvaluationNewFragment;
import com.example.administrator.jipinshop.fragment.home.HomeNewFragment;
import com.example.administrator.jipinshop.fragment.home.KTHomeFragnent;
import com.example.administrator.jipinshop.fragment.mine.MineFragment;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.FreeNewFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.InputMethodManagerLeak;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.example.administrator.jipinshop.util.NotificationUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;
import com.example.administrator.jipinshop.util.UpDataUtil;
import com.example.administrator.jipinshop.util.share.MobLinkUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.viewpager.NoScrollViewPager;
import com.gyf.barlibrary.ImmersionBar;
import com.mob.moblink.MobLink;
import com.mob.moblink.Scene;
import com.mob.moblink.SceneRestorable;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends RxAppCompatActivity implements MainView, ViewPager.OnPageChangeListener, SceneRestorable {

    @Inject
    MainActivityPresenter mPresenter;

    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.login_notice)
    TextView mLoginNotice;
    @BindView(R.id.dialog_minute)
    TextView mDialogMinute;
    @BindView(R.id.dialog_second)
    TextView mDialogSecond;
    @BindView(R.id.login_timeContainer)
    LinearLayout mLoginTimeContainer;
    @BindView(R.id.login_background)
    RelativeLayout mLoginBackground;

    private List<Fragment> mFragments;
    private HomeAdapter mHomeAdapter;
    private HomeNewFragment mHomeFragment;
    private MineFragment mMineFragment;
    private EvaluationNewFragment mEvaluationFragment;
    private FreeNewFragment mTryFragment;
    private KTHomeFragnent mKTHomeFragnent;

    private Unbinder mButterKnife;
    private ImmersionBar mImmersionBar;
    private long exitTime = 0;
    private static Activity sFirstInstance;
    private Boolean once = true; // 是否是第一次弹出
    private CountDownTimer mCountDownTimerUtils;//定时器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (null == sFirstInstance) {
            sFirstInstance = this;
        } else if (this != sFirstInstance) {
            // 防止微信跳转过来，多个MainActivity界面(是singletop)
            finish();
        }
        mButterKnife = ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 28) {
            //适配9.0刘海
            NotchUtil.notch(this);
        }
        initView();
    }

    private void initView() {
        DaggerBaseActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getComponent())
                .build().inject(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mPresenter.setView(this);
        EventBus.getDefault().register(this);

        mViewPager.setNoScroll(true);
        mFragments = new ArrayList<>();

        mKTHomeFragnent = KTHomeFragnent.getInstance();
        mEvaluationFragment = new EvaluationNewFragment();
        mHomeFragment = new HomeNewFragment();
        mTryFragment = new FreeNewFragment();
        mMineFragment = new MineFragment();
        mFragments.add(mKTHomeFragnent);
        mFragments.add(mEvaluationFragment);
        mFragments.add(mHomeFragment);
        mFragments.add(mTryFragment);
        mFragments.add(mMineFragment);

        mHomeAdapter = new HomeAdapter(getSupportFragmentManager());
        mHomeAdapter.setFragments(mFragments);
        mViewPager.setAdapter(mHomeAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
        mPresenter.initTabLayout(this, mTabLayout);
        UAppUtil.tab(this, 0);//统计榜单
        View tabView = (View) mTabLayout.getTabAt(mFragments.size() - 1).getCustomView().getParent();
        tabView.setOnClickListener(v -> {
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                if (ClickUtil.isFastDoubleClick(800)) {
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), 100);
                }
            }
        });

//        DistanceHelper.getAndroiodScreenProperty(this);
        if (SPUtils.getInstance().getBoolean(CommonDate.FIRST, true)) {
            //新人第一次进入app
//            mGuildBackground.setVisibility(View.VISIBLE);新手指导
            mLoginBackground.setVisibility(View.VISIBLE);
            mLoginNotice.setText("首单全免资格即将过期");
            mLoginTimeContainer.setVisibility(View.VISIBLE);
            setCountDownTimer();
        } else {
            //老人进入app
            mLoginNotice.setText("登录领取淘宝隐藏优惠券");
            mLoginTimeContainer.setVisibility(View.GONE);
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                mLoginBackground.setVisibility(View.VISIBLE);
            }else {
                mLoginBackground.setVisibility(View.GONE);
            }
        }
        mPresenter.getAppVersion(this.bindToLifecycle()); //版本更新
    }

    private void setCountDownTimer() {
        long time = 30 * 60;
        mCountDownTimerUtils =  new CountDownTimer(time * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int ss = 1000;
                int mi = ss * 60;
                int hh = mi * 60;
                int dd = hh * 24;
                long day = millisUntilFinished / dd;
                long hour = ((millisUntilFinished - day * dd) / hh);
                long minute = (millisUntilFinished- hour * hh - day * dd) / mi;
                long second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss;
                String s_minute= minute+"";
                String s_second= second+"";
                if(s_second.length()==1){
                    s_second="0"+s_second;
                }
                if(s_minute.length()==1){
                    s_minute="0"+s_minute;
                }
                mDialogMinute.setText(s_minute);
                mDialogSecond.setText(s_second);
            }

            public void onFinish() {
                mLoginNotice.setText("登录领取淘宝隐藏优惠券");
                mLoginTimeContainer.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        InputMethodManagerLeak.fixInputMethodManagerLeak(null, this);
        mImmersionBar.destroy();
        mButterKnife.unbind();
        EventBus.getDefault().unregister(this);
        if (sFirstInstance == this) {
            sFirstInstance = null;
        }
        if(mCountDownTimerUtils != null){
            mCountDownTimerUtils.cancel();
            mCountDownTimerUtils = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.show("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 400:
                TabLayout.Tab tab = mTabLayout.getTabAt(0);
                if (tab != null) {
                    tab.select();
                }
                break;
        }
        if (requestCode == 401) {//监听从设置页面返回来后调用
            //解决从设置页面跳转回来无法弹出dialog
            mPresenter.getPopInfo(MainActivity.this.bindToLifecycle());
        }
    }

    @Override
    public void onSuccess(AppVersionbean versionbean) {//版本更新弹出
        RetrofitModule.needVerify = versionbean.getData().getNeedVerify();
        if (versionbean.getData().getVersionCode() > UpDataUtil.getPackageVersionCode()) {
            if (versionbean.getData().getNeedUpdate() == 0) {
                //可以取消
                UpDataUtil.newInstance().downloadApk(this, versionbean.getData().getVersionName(), false,
                        versionbean.getData().getContent(), versionbean.getData().getDownloadUrl(), () -> {
                            if (SPUtils.getInstance().getBoolean(CommonDate.FIRST, true)) {
                                //新人第一次进入app
                                NotificationUtil.OpenNotificationSetting(this, () -> {
                                    //活动弹窗
                                    mPresenter.getPopInfo(MainActivity.this.bindToLifecycle());
                                });
                            } else {
                                //老人进入app
                                mPresenter.getPopInfo(this.bindToLifecycle()); //活动弹窗
                            }
                        });//第一版
            } else {
                //必须强制更新
                UpDataUtil.newInstance().downloadApk(this, versionbean.getData().getVersionName(), true,
                        versionbean.getData().getContent(), versionbean.getData().getDownloadUrl(), () -> {
                            if (SPUtils.getInstance().getBoolean(CommonDate.FIRST, true)) {
                                //新人第一次进入app
                                NotificationUtil.OpenNotificationSetting(this, () -> {
                                    //活动弹窗
                                    mPresenter.getPopInfo(MainActivity.this.bindToLifecycle());
                                });
                            } else {
                                //老人进入app
                                mPresenter.getPopInfo(this.bindToLifecycle()); //活动弹窗
                            }
                        });//第一版
            }

        } else {
            if (SPUtils.getInstance().getBoolean(CommonDate.FIRST, true)) {
                //新人第一次进入app
                NotificationUtil.OpenNotificationSetting(this, () -> {
                    //活动弹窗
                    mPresenter.getPopInfo(MainActivity.this.bindToLifecycle());
                });
            } else {
                //老人进入app
                mPresenter.getPopInfo(this.bindToLifecycle()); //活动弹窗
            }
        }
    }

    @Override
    public void onFile() {//版本更新没有弹出
        if (SPUtils.getInstance().getBoolean(CommonDate.FIRST, true)) {
            //新人第一次进入app
            NotificationUtil.OpenNotificationSetting(this, () -> {
                //活动弹窗
                mPresenter.getPopInfo(MainActivity.this.bindToLifecycle());
            });
        } else {
            //老人进入app
            mPresenter.getPopInfo(this.bindToLifecycle()); //活动弹窗
        }
    }

    @Override
    public void onDialogSuc(PopInfoBean bean) {
        if (bean.getData() != null && bean.getData().getData() != null) {
            if (bean.getData().getType() == 1) {
                //活动
                if (bean.getData().getData().getTargetType().equals("5")) {
                    if (!bean.getData().getPopId().equals(SPUtils.getInstance().getString(CommonDate.POPID, ""))) {
                        DialogUtil.imgDialog(MainActivity.this, bean.getData().getData().getImg(), v -> {
                            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                                startActivity(new Intent(this, LoginActivity.class)
                                        .putExtra("newpeople", 1));
                                return;
                            }
                            startActivity(new Intent(this, NewPeopleActivity.class));
                        }, v -> {
                            getClipText();
                        });
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            SPUtils.getInstance().put(CommonDate.POPID, bean.getData().getPopId());//未登录时存上id
                        }
                    } else {
                        getClipText();
                    }
                } else {
                    DialogUtil.imgDialog(MainActivity.this, bean.getData().getData().getImg(), v -> {
                        ShopJumpUtil.openPager(MainActivity.this, bean.getData().getData().getTargetType()
                                , bean.getData().getData().getTargetId(), "小分类");
                    }, v -> {
                        getClipText();
                    });
                }
            } else {
                //免单
                DialogUtil.freeDialog(this, bean, v -> {
                    startActivity(new Intent(MainActivity.this, FreeDetailActivity.class)
                            .putExtra("id", bean.getData().getData().getId())
                            .putExtra("fromId", bean.getData().getPopId())
                            .putExtra("fromType", "1")
                    );
                }, v -> {
                    getClipText();
                });
            }
        } else {
            getClipText();
        }
        if (once) {
            once = false;
        }
    }

    @Override
    public void onDialogFile() {
        getClipText();
        if (once) {
            once = false;
        }
    }

    @Subscribe
    public void changePage(ChangeHomePageBus bus) {
        if (bus != null) {
            mViewPager.setCurrentItem(bus.getPage());
        }
    }

    @Subscribe
    public void addPoint(HomeNewPeopleBus newPeopleBus) {
        if (newPeopleBus != null) {
//            DialogUtil.NewPeopleDialog(MainActivity.this, newPeopleBus.getAddPoint() + "", v -> {
//                startActivity(new Intent(MainActivity.this, SignActivity.class));
//            });
            mPresenter.getPopInfo(MainActivity.this.bindToLifecycle());
            mLoginNotice.setText("登录领取淘宝隐藏优惠券");
            mLoginTimeContainer.setVisibility(View.GONE);
            mLoginBackground.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (!once) {
            getClipText();//淘口令
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        UAppUtil.tab(this, i);
        if (i == 0){
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                mLoginBackground.setVisibility(View.VISIBLE);
            }else {
                mLoginBackground.setVisibility(View.GONE);
            }
        }else {
            mLoginBackground.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onReturnSceneData(Scene scene) {
        String id = (String) scene.params.get("id");
        MobLinkUtil.openStartActivity(this, scene.path, id);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MobLink.updateNewIntent(getIntent(), this);
    }


    public void getClipText() {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = cm.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            ClipData.Item item = clipData.getItemAt(0);
            if (item != null && item.getText() != null) {//小米手机会崩溃
                String content = item.getText().toString();
                if (!TextUtils.isEmpty(content.trim()) && !content.equals(SPUtils.getInstance().getString(CommonDate.CLIP, ""))) {
                    mPresenter.getGoodsByTkl(content, this.bindToLifecycle());
                }
            }
        }
    }

    //淘口令返回
    @Override
    public void onTklDialog(TklBean bean, String tkl) {
        DialogUtil.tklDialog(this, bean, tkl);
        SPUtils.getInstance().put(CommonDate.CLIP, tkl);
    }

    @OnClick({R.id.login_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_go:
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    if (ClickUtil.isFastDoubleClick(800)) {
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                }
                break;
        }
    }
}
