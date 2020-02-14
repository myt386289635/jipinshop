package com.example.administrator.jipinshop.activity.home;

import android.app.Activity;
import android.app.Dialog;
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
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewPeopleActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.DaggerBaseActivityComponent;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeNewPeopleBus;
import com.example.administrator.jipinshop.fragment.evaluationkt.EvaluationNewFragment;
import com.example.administrator.jipinshop.fragment.home.HomeNewFragment;
import com.example.administrator.jipinshop.fragment.home.KTHomeFragnent;
import com.example.administrator.jipinshop.fragment.mine.MineFragment;
import com.example.administrator.jipinshop.fragment.money.MoneyFragment;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.FreeNewFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.InputMethodManagerLeak;
import com.example.administrator.jipinshop.util.NotchUtil;
import com.example.administrator.jipinshop.util.NotificationUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.util.UmApp.UAppUtil;
import com.example.administrator.jipinshop.util.UpDataUtil;
import com.example.administrator.jipinshop.util.share.MobLinkUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
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
    @Inject
    AppStatisticalUtil appStatisticalUtil;

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
    private MoneyFragment mTryFragment;
    private KTHomeFragnent mKTHomeFragnent;

    private Unbinder mButterKnife;
    private ImmersionBar mImmersionBar;
    private long exitTime = 0;
    private static Activity sFirstInstance;
    private Boolean once = true; // 是否是第一次弹出
    private CountDownTimer mCountDownTimerUtils;//定时器
    private Dialog mDialog;

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
//        mTryFragment = new FreeNewFragment();
        mTryFragment = MoneyFragment.getInstance();//赚钱页面
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
        appStatisticalUtil.tab(0,this.bindToLifecycle());//统计榜单
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
                                    //20元津贴弹窗
                                    DialogUtil.NewPeopleDialog(MainActivity.this, v -> {
                                        getClipText();
                                    }, v -> {
                                        startActivity(new Intent(this, LoginActivity.class)
                                                .putExtra("newpeople", 1));
                                    });
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
                                    //20元津贴弹窗
                                    DialogUtil.NewPeopleDialog(MainActivity.this, v -> {
                                        getClipText();
                                    }, v -> {
                                        startActivity(new Intent(this, LoginActivity.class)
                                                .putExtra("newpeople", 1));
                                    });
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
                    //20元津贴弹窗
                    DialogUtil.NewPeopleDialog(MainActivity.this, v -> {
                        getClipText();
                    }, v -> {
                        startActivity(new Intent(this, LoginActivity.class)
                                .putExtra("newpeople", 1));
                    });
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
                //20元津贴弹窗
                DialogUtil.NewPeopleDialog(MainActivity.this, v -> {
                    getClipText();
                }, v -> {
                    startActivity(new Intent(this, LoginActivity.class)
                            .putExtra("newpeople", 1));
                });
            });
        } else {
            //老人进入app
            mPresenter.getPopInfo(this.bindToLifecycle()); //活动弹窗
        }
    }

    @Override
    public void onDialogSuc(PopInfoBean bean) {
        if (bean.getData() != null && bean.getData().size() != 0) {//有弹窗
            if (bean.getData().size() > 1){
                //有2个以上弹窗  目前逻辑：新人、活动、首单弹窗都只会有一个;新人与首单互斥
                int newPos = -1;//新人弹窗的位置
                int activityPos = -1;//活动弹窗的位置
                int oldPos = -1;//首返弹窗的位置
                for (int i = 0; i < bean.getData().size(); i++) {
                    if (bean.getData().get(i).getType() == 0){
                        newPos = i;
                    }else if (bean.getData().get(i).getType() == 1){
                        activityPos = i;
                    }else if (bean.getData().get(i).getType() == 3){
                        oldPos = i;
                    }
                }
                if (newPos != -1){
                    //新人弹窗 + 活动弹窗
                    if (!bean.getData().get(newPos).getPopId().equals(SPUtils.getInstance().getString(CommonDate.POPID, ""))) {
                        int finalActivityPos = activityPos;
                        DialogUtil.imgDialog(MainActivity.this, bean.getData().get(newPos).getData().getImg(), v -> {
                            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                                startActivity(new Intent(this, LoginActivity.class)
                                        .putExtra("newpeople", 1));
                                return;
                            }
                            startActivity(new Intent(this, NewPeopleActivity.class));
                        }, v -> {
                            DialogUtil.imgDialog(MainActivity.this, bean.getData().get(finalActivityPos).getData().getImg(), v1 -> {
                                ShopJumpUtil.openPager(MainActivity.this, bean.getData().get(finalActivityPos).getData().getTargetType()
                                        , bean.getData().get(finalActivityPos).getData().getTargetId(), "小分类");
                            }, v1 -> {
                                getClipText();
                            });
                        });
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            SPUtils.getInstance().put(CommonDate.POPID, bean.getData().get(newPos).getPopId());//未登录时存上id
                        }
                    } else {
                        getClipText();
                    }
                }else if (oldPos != -1){
                    //首返 + 活动弹窗
                    if (bean.getData().get(oldPos).getAllowanceGoodsList().size() >= 3){
                        int finalActivityPos1 = activityPos;
                        DialogUtil.cheapDialog(this, bean.getData().get(oldPos).getAddAllowancePrice(),
                                bean.getData().get(oldPos).getAllowanceGoodsList(), v2 -> {
                                    startActivity(new Intent(this, CheapBuyActivity.class));
                                }, v2 -> {
                                    DialogUtil.imgDialog(MainActivity.this, bean.getData().get(finalActivityPos1).getData().getImg(), v -> {
                                        ShopJumpUtil.openPager(MainActivity.this, bean.getData().get(finalActivityPos1).getData().getTargetType()
                                                , bean.getData().get(finalActivityPos1).getData().getTargetId(), "小分类");
                                    }, v -> {
                                        getClipText();
                                    });
                                });
                    }else {
                        int finalActivityPos2 = activityPos;
                        DialogUtil.imgDialog(MainActivity.this, bean.getData().get(finalActivityPos2).getData().getImg(), v3 -> {
                            ShopJumpUtil.openPager(MainActivity.this, bean.getData().get(finalActivityPos2).getData().getTargetType()
                                    , bean.getData().get(finalActivityPos2).getData().getTargetId(), "小分类");
                        }, v3 -> {
                            getClipText();
                        });
                    }
                }else {
                    getClipText();
                }
            }else {
                //只有一个弹窗
                if (bean.getData().get(0).getType() == 1) {
                    //系统活动弹窗
                    DialogUtil.imgDialog(MainActivity.this, bean.getData().get(0).getData().getImg(), v -> {
                        ShopJumpUtil.openPager(MainActivity.this, bean.getData().get(0).getData().getTargetType()
                                , bean.getData().get(0).getData().getTargetId(), "小分类");
                    }, v -> {
                        getClipText();
                    });
                }else if (bean.getData().get(0).getType() == 0){
                    //新人弹窗
                    if (!bean.getData().get(0).getPopId().equals(SPUtils.getInstance().getString(CommonDate.POPID, ""))) {
                        DialogUtil.imgDialog(MainActivity.this, bean.getData().get(0).getData().getImg(), v -> {
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
                            SPUtils.getInstance().put(CommonDate.POPID, bean.getData().get(0).getPopId());//未登录时存上id
                        }
                    } else {
                        getClipText();
                    }
                }else if (bean.getData().get(0).getType() == 3){
                    //首单弹窗
                    if (bean.getData().get(0).getAllowanceGoodsList().size() >= 3){
                        DialogUtil.cheapDialog(this, bean.getData().get(0).getAddAllowancePrice(),
                                bean.getData().get(0).getAllowanceGoodsList(), v -> {
                                    startActivity(new Intent(this, CheapBuyActivity.class));
                                }, v -> {
                                    getClipText();
                                });
                    }else {
                        getClipText();
                    }
                }else {
                    getClipText();
                }
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
            if (newPeopleBus.getAddPoint() != 1){
                mPresenter.getPopInfo(MainActivity.this.bindToLifecycle());
            }
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
        appStatisticalUtil.tab(i,this.bindToLifecycle());//统计榜单
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
        if (bean.getData() != null && bean.getData().getType() == 2){
            //关联用户
            DialogUtil.userDialog(this, bean, (invitationCode, dialog, inputManager) -> {
                mDialog = (new ProgressDialogView()).createLoadingDialog(MainActivity.this, "");
                mDialog.show();
                mPresenter.addInvitationCode(invitationCode, dialog,this.bindToLifecycle());
            });
        }else {
            //淘口令返回
            DialogUtil.tklDialog(this, bean, tkl);
        }
        SPUtils.getInstance().put(CommonDate.CLIP, tkl);
    }

    @Override
    public void onInvitationSuc(Dialog dialog) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        ToastUtil.show("关联成功");
        EventBus.getDefault().post(new EditNameBus(SignActivity.eventbusTag));
    }

    @Override
    public void onInvitationFile(String error) {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
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
