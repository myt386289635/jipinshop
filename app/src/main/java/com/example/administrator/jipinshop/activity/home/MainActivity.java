package com.example.administrator.jipinshop.activity.home;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.PopBean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeNewPeopleBus;
import com.example.administrator.jipinshop.databinding.ActivityMainBinding;
import com.example.administrator.jipinshop.fragment.evaluationkt.EvaluationNewFragment;
import com.example.administrator.jipinshop.fragment.home.KTHomeFragnent;
import com.example.administrator.jipinshop.fragment.member.MemberFragment;
import com.example.administrator.jipinshop.fragment.mine.KTMineFragment;
import com.example.administrator.jipinshop.fragment.play.PlayFragment;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.NotificationUtil;
import com.example.administrator.jipinshop.util.ShareUtils;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.util.UpDataUtil;
import com.example.administrator.jipinshop.util.share.MobLinkUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
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
import com.umeng.socialize.bean.SHARE_MEDIA;

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
    private EvaluationNewFragment mCircleFragment;//评测
    private KTHomeFragnent mKTHomeFragnent;//首页
    private PlayFragment mPlayFragment;//吃喝玩乐

    private long exitTime = 0;
    private Boolean once = true; // 是否是第一次弹出
    private Dialog mDialog;
    private ImageWatcherHelper iwHelper;
    private DecorationLayout layDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("isAd", false)) {
            String targetType = getIntent().getStringExtra("targetType");
            String target_id = getIntent().getStringExtra("target_id");
            String target_title = getIntent().getStringExtra("target_title");
            String source = getIntent().getStringExtra("source");
            String remark = getIntent().getStringExtra("remark");
            if (!targetType.equals("101") && !targetType.equals("102") && !targetType.equals("103")
                    && !targetType.equals("104") && !targetType.equals("105")) {
                ShopJumpUtil.openBanner(this, targetType, target_id, target_title, source,remark);
            }
        }
        if (getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_VIEW)){
            Uri uri = getIntent().getData();
            if (uri != null && uri.getHost() != null && uri.getHost().equals("group")){
                //跳转到详情
                startActivity(new Intent(this, TBShoppingDetailActivity.class)
                        .putExtra("otherGoodsId", uri.getQueryParameter("id"))
                        .putExtra("source",uri.getQueryParameter("source"))
                        .putExtra("groupid",uri.getQueryParameter("groupid"))
                        .putExtra("isH5",true)
                );
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
        mPlayFragment = PlayFragment.getInstance();
        mMemberFragment = MemberFragment.getInstance("1");
        mCircleFragment = EvaluationNewFragment.getInstance("1");
        mMineFragment = KTMineFragment.getInstance();
        mFragments.add(mKTHomeFragnent);
        mFragments.add(mPlayFragment);
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
        mBinding.loginNotice.setText("登录开启网购省钱之旅");
        mBinding.loginTimeContainer.setVisibility(View.GONE);
        if (SPUtils.getInstance().getBoolean(CommonDate.FIRST, true)) {
            //新人第一次进入app
            mBinding.loginBackground.setVisibility(View.VISIBLE);
            mPresenter.getAppVersion(this.bindToLifecycle()); //版本更新
        } else {
            //老人进入app
            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, "").trim())) {
                mBinding.loginBackground.setVisibility(View.VISIBLE);
            } else {
                mBinding.loginBackground.setVisibility(View.GONE);
            }
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
            String remark = getIntent().getStringExtra("remark");
            if (targetType.equals("101") || targetType.equals("102") || targetType.equals("103")
                    || targetType.equals("104") || targetType.equals("105")) {
                ShopJumpUtil.openBanner(this, targetType, target_id, target_title, source,remark);
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
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
            DialogUtil.newPeopleDialog(MainActivity.this, "https://jipincheng.cn/app_first?",v -> {
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
                DialogUtil.newPeopleDialog(MainActivity.this,"https://jipincheng.cn/app_first?", v -> {
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

    //其他操作，现在不要新手指导了
    public void onGuide(){
        DialogUtil.newPeopleDialog(MainActivity.this,"https://jipincheng.cn/app_vip1?", v -> {
            getClipText();
        }, v -> {
            EventBus.getDefault().post(new ChangeHomePageBus(2));
            getClipText();
        });
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
                                        , bean.getData().get(finalActivityPos).getData().getTargetId(), bean.getData().get(finalActivityPos).getData().getTitle(),
                                        bean.getData().get(finalActivityPos).getData().getSource(),bean.getData().get(finalActivityPos).getData().getRemark());
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
                                    , bean.getData().get(finalActivityPos).getData().getTargetId(), bean.getData().get(finalActivityPos).getData().getTitle(),
                                    bean.getData().get(finalActivityPos).getData().getSource(),bean.getData().get(finalActivityPos).getData().getRemark());
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
                                    , bean.getData().get(finalActivityPos1).getData().getTargetId(), bean.getData().get(finalActivityPos1).getData().getTitle(),
                                    bean.getData().get(finalActivityPos1).getData().getSource(),bean.getData().get(finalActivityPos1).getData().getRemark());
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
                                , bean.getData().get(0).getData().getTargetId(), bean.getData().get(0).getData().getTitle(),
                                bean.getData().get(0).getData().getSource(),bean.getData().get(0).getData().getRemark());
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
            mBinding.loginNotice.setText("登录开启网购省钱之旅");
            mBinding.loginTimeContainer.setVisibility(View.GONE);
            mBinding.loginBackground.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        mPresenter.getGroupDialog(this.bindToLifecycle());//拼团下单后弹窗
        if (!once) {
            //android 10 新api解释是只有当前界面获得焦点后（onResume）才能获取到剪切板内容
            this.getWindow().getDecorView().post(() -> {
                getClipText();//淘口令
            });
        }
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    //首次下单后弹框
    @Override
    public void onNewDialogSuc(PopBean bean) {
        if (bean.getData() != null) {
            DialogUtil.cheapDialog(this, v12 -> {
                startActivity(new Intent(this, CheapBuyActivity.class));
            }, null);
        }
    }

    //拼团下单后弹窗
    @Override
    public void onGroupDialogSuc(PopBean bean) {
        if (bean != null && bean.getData() != null){
            DialogUtil.groupDialog(this, bean.getData().getGroupGoods(), v -> {
                //分享
                mDialog = (new ProgressDialogView()).createLoadingDialog(MainActivity.this, "");
                mDialog.show();
                mPresenter.initShare(bean.getData().getGroupGoods().getId(),this.bindToLifecycle());
            }, v -> {
                mPresenter.getNewDialog(this.bindUntilEvent(ActivityEvent.DESTROY));//获取108元津贴弹窗问题
            });
        }else {
            mPresenter.getNewDialog(this.bindToLifecycle());//获取108元津贴弹窗问题
        }
    }

    //拼团分享
    @Override
    public void initShare(ShareInfoBean bean) {
        new ShareUtils(this, SHARE_MEDIA.WEIXIN,mDialog)
                .shareWeb(this, bean.getData().getLink(),bean.getData().getTitle(),
                        bean.getData().getDesc(),bean.getData().getImgUrl(),R.mipmap.share_logo);
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

}
