package com.example.administrator.jipinshop.activity.home;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PopBean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeNewPeopleBus;
import com.example.administrator.jipinshop.databinding.ActivityMainBinding;
import com.example.administrator.jipinshop.fragment.circle.CircleFragment;
import com.example.administrator.jipinshop.fragment.home.KTHomeFragnent;
import com.example.administrator.jipinshop.fragment.member.MemberFragment;
import com.example.administrator.jipinshop.fragment.mine.KTMineFragment;
import com.example.administrator.jipinshop.fragment.school.KTSchoolFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.NotificationUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.util.UpDataUtil;
import com.example.administrator.jipinshop.util.share.MobLinkUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.example.administrator.jipinshop.view.pick.CustomLoadingUIProvider2;
import com.example.administrator.jipinshop.view.pick.DecorationLayout;
import com.example.administrator.jipinshop.view.pick.GlideSimpleLoader;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.google.gson.Gson;
import com.mob.moblink.MobLink;
import com.mob.moblink.Scene;
import com.mob.moblink.SceneRestorable;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView, ViewPager.OnPageChangeListener, SceneRestorable, ImageWatcherHelper.Provider, View.OnClickListener {

    @Inject
    MainActivityPresenter mPresenter;
    @Inject
    AppStatisticalUtil appStatisticalUtil;

    private ActivityMainBinding mBinding;
    private List<Fragment> mFragments;
    private HomeAdapter mHomeAdapter;
    private MemberFragment mMemberFragment;//新版会员
    private KTMineFragment mMineFragment;//我的
    private CircleFragment mCircleFragment;//发圈
    private KTHomeFragnent mKTHomeFragnent;//首页
    private KTSchoolFragment mSchoolFragment;//商学院

    private long exitTime = 0;
    private Boolean once = true; // 是否是第一次弹出
    private CountDownTimer mCountDownTimerUtils;//定时器
    private Dialog mDialog;
    private ImageWatcherHelper iwHelper;
    private DecorationLayout layDecoration;
    private Boolean isGuide = false;//是否需要打开新手指导

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("isAd", false)) {
            String targetType = getIntent().getStringExtra("targetType");
            String target_id = getIntent().getStringExtra("target_id");
            String target_title = getIntent().getStringExtra("target_title");
            String source = getIntent().getStringExtra("source");
            if (!targetType.equals("101") && !targetType.equals("102") && !targetType.equals("103")
                    && !targetType.equals("104") && !targetType.equals("105")) {
                ShopJumpUtil.openBanner(this, targetType, target_id, target_title, source);
            }
        }
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mBinding.setListener(this);
        initView();
    }

    private void initView() {
        mBaseActivityComponent.inject(this);
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init();
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        pickImages();//初始化浏览图片

        mBinding.viewPager.setNoScroll(true);
        mFragments = new ArrayList<>();

        mKTHomeFragnent = KTHomeFragnent.getInstance();
        mSchoolFragment = KTSchoolFragment.getInstance();
        mMemberFragment = MemberFragment.getInstance("1");
        mCircleFragment = CircleFragment.getInstance();
        mMineFragment = KTMineFragment.getInstance();
        mFragments.add(mKTHomeFragnent);
        mFragments.add(mSchoolFragment);
        mFragments.add(mMemberFragment);
        mFragments.add(mCircleFragment);
        mFragments.add(mMineFragment);

        mHomeAdapter = new HomeAdapter(getSupportFragmentManager());
        mHomeAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mHomeAdapter);
        mBinding.viewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.viewPager.addOnPageChangeListener(this);
        mPresenter.initTabLayout(this, mBinding.tabLayout);
        mPresenter.initTab(this, mBinding.tabLayout, mFragments);//初始化tab拦截事件
        appStatisticalUtil.tab(0, this.bindToLifecycle());//统计首页

        mPresenter.getPrivateVersion(this.bindToLifecycle());//获取隐私协议版本号
        mPresenter.adList(this.bindToLifecycle());//app广告
    }

    //隐私协议获取返回
    @Override
    public void onStartPrivate(ImageBean bean) {
        onResult(bean.getData());
    }

    @Override
    public void onStartFile() {
        onResult("");
    }

    public void onResult(String currentPrivacy) {
//        isGuide = getIntent().getBooleanExtra("isGuide", false);
        if (SPUtils.getInstance().getBoolean(CommonDate.FIRST, true)) {
            //新人第一次进入app
            mBinding.loginBackground.setVisibility(View.VISIBLE);
            mBinding.loginNotice.setText("新人0元免单资格即将过期");
            mBinding.loginTimeContainer.setVisibility(View.VISIBLE);
            setCountDownTimer();
            mBinding.guideContainer.setVisibility(View.GONE);
            mPresenter.getAppVersion(this.bindToLifecycle()); //版本更新
        } else {
            //老人进入app
            mBinding.loginNotice.setText("登录领取淘宝隐藏优惠券");
            mBinding.loginTimeContainer.setVisibility(View.GONE);
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                mBinding.loginBackground.setVisibility(View.VISIBLE);
            } else {
                mBinding.loginBackground.setVisibility(View.GONE);
            }
            mBinding.guideContainer.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(currentPrivacy) && !currentPrivacy.equals(SPUtils.getInstance().getString(CommonDate.privacy, ""))) {
                DialogUtil.servceDialog(this, v -> {
                    mPresenter.getAppVersion(this.bindToLifecycle()); //版本更新
                }, v -> {
                    //关闭App
                    finish();
                });
            } else {
                mPresenter.getAppVersion(this.bindToLifecycle()); //版本更新
            }
        }
        if (!TextUtils.isEmpty(currentPrivacy)) {//存入隐私协议版本号
            SPUtils.getInstance().put(CommonDate.privacy, currentPrivacy);
        }
        //需要滑动app的变态需求
        if (getIntent().getBooleanExtra("isAd", false)) {
            String targetType = getIntent().getStringExtra("targetType");
            String target_id = getIntent().getStringExtra("target_id");
            String target_title = getIntent().getStringExtra("target_title");
            String source = getIntent().getStringExtra("source");
            if (targetType.equals("101") || targetType.equals("102") || targetType.equals("103")
                    || targetType.equals("104") || targetType.equals("105")) {
                ShopJumpUtil.openBanner(this, targetType, target_id, target_title, source);
            }
        }
    }

    private void setCountDownTimer() {
        long time = 30 * 60;
        mCountDownTimerUtils = new CountDownTimer(time * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int ss = 1000;
                int mi = ss * 60;
                int hh = mi * 60;
                int dd = hh * 24;
                long day = millisUntilFinished / dd;
                long hour = ((millisUntilFinished - day * dd) / hh);
                long minute = (millisUntilFinished - hour * hh - day * dd) / mi;
                long second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss;
                String s_minute = minute + "";
                String s_second = second + "";
                if (s_second.length() == 1) {
                    s_second = "0" + s_second;
                }
                if (s_minute.length() == 1) {
                    s_minute = "0" + s_minute;
                }
                mBinding.dialogMinute.setText(s_minute);
                mBinding.dialogSecond.setText(s_second);
            }

            public void onFinish() {
                mBinding.loginNotice.setText("登录领取淘宝隐藏优惠券");
                mBinding.loginTimeContainer.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mCountDownTimerUtils != null) {
            mCountDownTimerUtils.cancel();
            mCountDownTimerUtils = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!iwHelper.handleBackPressed()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.show("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    //浏览图片初始化控件
    public void pickImages() {
        layDecoration = new DecorationLayout(this);
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOtherView(layDecoration)
                .addOnPageChangeListener(layDecoration)
                .setLoadingUIProvider(new CustomLoadingUIProvider2()); // 自定义LoadingUI
        layDecoration.attachImageWatcher(iwHelper);
    }

    @Override
    public ImageWatcherHelper iwHelper() {
        return iwHelper;
    }

    public DecorationLayout getLayDecoration() {
        return layDecoration;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 401) {//监听从设置页面返回来后调用
            //解决从设置页面跳转回来无法弹出dialog
            //20元津贴弹窗
            DialogUtil.newPeopleDialog(MainActivity.this, v -> {
                appStatisticalUtil.addEvent("tc.xr_close", this.bindToLifecycle());
                onCheapDialog();
            }, v -> {
                appStatisticalUtil.addEvent("tc.xr_enter", this.bindUntilEvent(ActivityEvent.DESTROY));
                startActivity(new Intent(this, NewFreeActivity.class)
                        .putExtra("startPop", false)
                );
                onCheapDialog();
            });
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
                            onFile();
                        });//第一版
            } else {
                //必须强制更新
                UpDataUtil.newInstance().downloadApk(this, versionbean.getData().getVersionName(), true,
                        versionbean.getData().getContent(), versionbean.getData().getDownloadUrl(), () -> {
                            onFile();
                        });//第一版
            }

        } else {
            onFile();
        }
    }

    @Override
    public void onFile() {//版本更新没有弹出
        if (SPUtils.getInstance().getBoolean(CommonDate.FIRST, true)) {
            //新人第一次进入app
            NotificationUtil.OpenNotificationSetting(this, () -> {
                //新人0元购
                DialogUtil.newPeopleDialog(MainActivity.this, v -> {
                    appStatisticalUtil.addEvent("tc.xr_close", this.bindToLifecycle());
                    onCheapDialog();
                }, v -> {
                    appStatisticalUtil.addEvent("tc.xr_enter", this.bindUntilEvent(ActivityEvent.DESTROY));
                    startActivity(new Intent(this, NewFreeActivity.class)
                            .putExtra("startPop", false)
                    );
                    onCheapDialog();
                });
            });
        } else {
            //老人进入app
            mPresenter.getPopInfo(this.bindToLifecycle()); //活动弹窗
        }
    }

    //特惠购首次下单奖励弹框
    public void onCheapDialog() {
        DialogUtil.cheapDialog(this, v12 -> {
            appStatisticalUtil.addEvent("tc.thg_enter", this.bindUntilEvent(ActivityEvent.DESTROY));
            startActivity(new Intent(this, CheapBuyActivity.class)
                    .putExtra("startPop", false)
            );
            onGuide();
        }, v1 -> {
            appStatisticalUtil.addEvent("tc.thg_close", this.bindToLifecycle());
            onGuide();
        });
    }

    //新手指导
    public void onGuide(){
        if (isGuide) {
            //新手指导
            mPresenter.setStatusBarHight(mBinding.statusBar,this);
            GlideApp.loderImage(this, "https://jipincheng.cn/guide_head.gif", mBinding.guideHead1, 0, 0);
            GlideApp.loderImage(this, "https://jipincheng.cn/guide_head.gif", mBinding.guideHead2, 0, 0);
            mBinding.guideContainer.setVisibility(View.VISIBLE);
        }else {
            getClipText();
        }
    }

    @Override
    public void onDialogSuc(PopInfoBean bean) {
        if (bean.getData() != null && bean.getData().size() != 0) {//有弹窗
            if (bean.getData().size() > 1) {
                //有2个以上弹窗  目前逻辑：新人、活动、首单弹窗都只会有一个;新人与首单互斥
                int newPos = -1;//新人弹窗的位置
                int activityPos = -1;//活动弹窗的位置
                int oldPos = -1;//首返弹窗的位置
                for (int i = 0; i < bean.getData().size(); i++) {
                    if (bean.getData().get(i).getType() == 0) {
                        newPos = i;
                    } else if (bean.getData().get(i).getType() == 1) {
                        activityPos = i;
                    } else if (bean.getData().get(i).getType() == 3) {
                        oldPos = i;
                    }
                }
                if (newPos != -1) {
                    //新人弹窗 + 活动弹窗
                    int finalActivityPos = activityPos;
                    if (!bean.getData().get(newPos).getPopId().equals(SPUtils.getInstance().getString(CommonDate.POPID, ""))) {
                        DialogUtil.imgDialog(MainActivity.this, bean.getData().get(newPos).getData().getImg(), v -> {
                            startActivity(new Intent(this, NewFreeActivity.class));
                        }, v -> {
                            DialogUtil.imgDialog(MainActivity.this, bean.getData().get(finalActivityPos).getData().getImg(), v1 -> {
                                ShopJumpUtil.openPager(MainActivity.this, bean.getData().get(finalActivityPos).getData().getTargetType()
                                        , bean.getData().get(finalActivityPos).getData().getTargetId(), bean.getData().get(finalActivityPos).getData().getTitle());
                            }, v1 -> {
                                getClipText();
                            });
                        });
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            SPUtils.getInstance().put(CommonDate.POPID, bean.getData().get(newPos).getPopId());//未登录时存上id
                        }
                    } else {
                        DialogUtil.imgDialog(MainActivity.this, bean.getData().get(finalActivityPos).getData().getImg(), v1 -> {
                            ShopJumpUtil.openPager(MainActivity.this, bean.getData().get(finalActivityPos).getData().getTargetType()
                                    , bean.getData().get(finalActivityPos).getData().getTargetId(), bean.getData().get(finalActivityPos).getData().getTitle());
                        }, v1 -> {
                            getClipText();
                        });
                    }
                } else if (oldPos != -1) {
                    //首返 + 活动弹窗
                    int finalActivityPos1 = activityPos;
                    DialogUtil.cheapDialog(this, v2 -> {
                        startActivity(new Intent(this, CheapBuyActivity.class));
                    }, v2 -> {
                        DialogUtil.imgDialog(MainActivity.this, bean.getData().get(finalActivityPos1).getData().getImg(), v -> {
                            ShopJumpUtil.openPager(MainActivity.this, bean.getData().get(finalActivityPos1).getData().getTargetType()
                                    , bean.getData().get(finalActivityPos1).getData().getTargetId(), bean.getData().get(finalActivityPos1).getData().getTitle());
                        }, v -> {
                            getClipText();
                        });
                    });
                } else {
                    getClipText();
                }
            } else {
                //只有一个弹窗
                if (bean.getData().get(0).getType() == 1) {
                    //系统活动弹窗
                    DialogUtil.imgDialog(MainActivity.this, bean.getData().get(0).getData().getImg(), v -> {
                        ShopJumpUtil.openPager(MainActivity.this, bean.getData().get(0).getData().getTargetType()
                                , bean.getData().get(0).getData().getTargetId(), bean.getData().get(0).getData().getTitle());
                    }, v -> {
                        getClipText();
                    });
                } else if (bean.getData().get(0).getType() == 0) {
                    //新人弹窗
                    if (!bean.getData().get(0).getPopId().equals(SPUtils.getInstance().getString(CommonDate.POPID, ""))) {
                        DialogUtil.imgDialog(MainActivity.this, bean.getData().get(0).getData().getImg(), v -> {
                            startActivity(new Intent(this, NewFreeActivity.class));
                        }, v -> {
                            getClipText();
                        });
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            SPUtils.getInstance().put(CommonDate.POPID, bean.getData().get(0).getPopId());//未登录时存上id
                        }
                    } else {
                        getClipText();
                    }
                } else if (bean.getData().get(0).getType() == 3) {
                    //首单弹窗
                    DialogUtil.cheapDialog(this, v -> {
                        startActivity(new Intent(this, CheapBuyActivity.class));
                    }, v -> {
                        getClipText();
                    });
                } else {
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
            mBinding.viewPager.setCurrentItem(bus.getPage());
        }
    }

    @Subscribe
    public void addPoint(HomeNewPeopleBus newPeopleBus) {
        if (newPeopleBus != null) {
            if (newPeopleBus.getAddPoint() != 1) {
                mPresenter.getPopInfo(MainActivity.this.bindToLifecycle());
            }
            mBinding.loginNotice.setText("登录领取淘宝隐藏优惠券");
            mBinding.loginTimeContainer.setVisibility(View.GONE);
            mBinding.loginBackground.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        mPresenter.getNewDialog(this.bindToLifecycle());//获取108元津贴弹窗问题
        if (!once) {
            //android 10 新api解释是只有当前界面获得焦点后（onResume）才能获取到剪切板内容
            this.getWindow().getDecorView().post(() -> {
                getClipText();//淘口令
            });
        }
    }

    @Override
    public void onNewDialogSuc(PopBean bean) {
        if (bean.getData() != null) {
            DialogUtil.cheapDialog(this, v12 -> {
                startActivity(new Intent(this, CheapBuyActivity.class));
            }, null);
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
        appStatisticalUtil.tab(i, this.bindToLifecycle());//统计榜单
        if (i == 0) {
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                mBinding.loginBackground.setVisibility(View.VISIBLE);
            } else {
                mBinding.loginBackground.setVisibility(View.GONE);
            }
        } else {
            mBinding.loginBackground.setVisibility(View.GONE);
        }
        //改变状态栏颜色
        if (i == 2) {
            mImmersionBar.reset()
                    .transparentStatusBar()
                    .statusBarDarkFont(false, 0f)
                    .init();
            mBinding.memberNoticeContainer.setVisibility(View.GONE);
            SPUtils.getInstance().put(CommonDate.memberNotice, false);
        } else {
            mImmersionBar.reset()
                    .transparentStatusBar()
                    .statusBarDarkFont(true, 0f)
                    .init();
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
                    mPresenter.getGoodsByTkl(content, this.bindUntilEvent(ActivityEvent.DESTROY));
                }
            }
        }
    }

    //淘口令返回
    @Override
    public void onTklDialog(TklBean bean, String tkl) {
        if (bean.getData() != null && bean.getData().getType() == 2) {
            //关联用户
            DialogUtil.userDialog(this, bean, (invitationCode, dialog, inputManager) -> {
                mDialog = (new ProgressDialogView()).createLoadingDialog(MainActivity.this, "");
                mDialog.show();
                mPresenter.addInvitationCode(invitationCode, dialog, this.bindToLifecycle());
            });
        } else {
            //淘口令返回
            DialogUtil.tklDialog(this, bean, tkl);
        }
        SPUtils.getInstance().put(CommonDate.CLIP, tkl);
    }

    @Override
    public void onInvitationSuc(Dialog dialog) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        ToastUtil.show("关联成功");
        EventBus.getDefault().post(new EditNameBus(SignActivity.eventbusTag));
    }

    @Override
    public void onInvitationFile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    //app广告
    @Override
    public void onAdList(SucBean<EvaluationTabBean.DataBean.AdListBean> adListBeanSucBean) {
        if (adListBeanSucBean.getData() != null && adListBeanSucBean.getData().size() != 0) {
            //有广告
            String json = new Gson().toJson(adListBeanSucBean.getData().get(0), EvaluationTabBean.DataBean.AdListBean.class);
            SPUtils.getInstance().put(CommonDate.AD, json);
        } else {
            //没有广告
            SPUtils.getInstance().put(CommonDate.AD, "");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_go:
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                    if (ClickUtil.isFastDoubleClick(800)) {
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                }
                break;
            case R.id.member_notice_close:
                mBinding.memberNoticeContainer.setVisibility(View.GONE);
                SPUtils.getInstance().put(CommonDate.memberNotice, false);
                break;
            case R.id.guide_image1:
                //进入第二部
                appStatisticalUtil.addEvent("yindao3_next", this.bindToLifecycle());
                mBinding.nextOne.setVisibility(View.GONE);
                mBinding.nextTwo.setVisibility(View.VISIBLE);
                break;
            case R.id.guide_image2:
                //进入第三步
                appStatisticalUtil.addEvent("yindao4_next", this.bindToLifecycle());
                mBinding.nextOne.setVisibility(View.GONE);
                mBinding.nextTwo.setVisibility(View.GONE);
                mBinding.nextThree.setVisibility(View.VISIBLE);
                break;
            case R.id.guide_image3:
                //点击第三步的图片
                appStatisticalUtil.addEvent("yindao5_close", this.bindToLifecycle());
                mBinding.guideContainer.setVisibility(View.GONE);
                getClipText();
                break;
            case R.id.guide_dismiss1:
                //第一步里的跳过
                appStatisticalUtil.addEvent("yindao3_tiaoguo", this.bindToLifecycle());
                mBinding.guideContainer.setVisibility(View.GONE);
                getClipText();
                break;
            case R.id.guide_dismiss2:
                //第二步里的逃过
                appStatisticalUtil.addEvent("yindao4_tiaoguo", this.bindToLifecycle());
                mBinding.guideContainer.setVisibility(View.GONE);
                getClipText();
                break;
            case R.id.guide_dismiss3:
                //第三步里的跳过
                appStatisticalUtil.addEvent("yindao5_tiaoguo", this.bindToLifecycle());
                mBinding.guideContainer.setVisibility(View.GONE);
                getClipText();
                break;
            case R.id.guide_ok:
                //第三步里的我知道了
                appStatisticalUtil.addEvent("yindao5_zhidao", this.bindToLifecycle());
                mBinding.guideContainer.setVisibility(View.GONE);
                getClipText();
                break;
        }
    }

    //新版会员提示语
    public void memberNotice(Boolean isShow){
        if (isShow){
            mBinding.memberNoticeContainer.setVisibility(View.VISIBLE);
        }else {
            mBinding.memberNoticeContainer.setVisibility(View.GONE);
        }
    }

    //会员引导页蒙尘
    public void memberGuide(Boolean isShow){
        if (isShow){
            mBinding.memberGuideBg.setVisibility(View.VISIBLE);
        }else {
            mBinding.memberGuideBg.setVisibility(View.GONE);
        }
    }
}
