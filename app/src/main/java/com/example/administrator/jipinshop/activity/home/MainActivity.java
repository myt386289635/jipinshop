package com.example.administrator.jipinshop.activity.home;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.newpeople.NewPeopleActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeDetailActivity;
import com.example.administrator.jipinshop.adapter.HomeAdapter;
import com.example.administrator.jipinshop.base.DaggerBaseActivityComponent;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus;
import com.example.administrator.jipinshop.bean.eventbus.HomeNewPeopleBus;
import com.example.administrator.jipinshop.fragment.activity11.Action11Fragment;
import com.example.administrator.jipinshop.fragment.evaluationkt.EvaluationNewFragment;
import com.example.administrator.jipinshop.fragment.home.HomeNewFragment;
import com.example.administrator.jipinshop.fragment.mine.MineFragment;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.FreeFragment;
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
    @BindView(R.id.guide_content1)
    ImageView mGuideContent1;
    @BindView(R.id.guide_next1)
    ImageView mGuideNext1;
    @BindView(R.id.guide_content2)
    ImageView mGuideContent2;
    @BindView(R.id.guide_next2)
    ImageView mGuideNext2;
    @BindView(R.id.guide_content3)
    ImageView mGuideContent3;
    @BindView(R.id.guide_next3)
    ImageView mGuideNext3;
    @BindView(R.id.guild_background)
    RelativeLayout mGuildBackground;
    @BindView(R.id.guild_background2)
    LinearLayout mGuildBackground2;

    private List<Fragment> mFragments;
    private HomeAdapter mHomeAdapter;
    private HomeNewFragment mHomeFragment;
    private MineFragment mMineFragment;
    private EvaluationNewFragment mEvaluationFragment;
    private FreeNewFragment mTryFragment;
    private Action11Fragment mAction11Fragment;

    private Unbinder mButterKnife;
    private ImmersionBar mImmersionBar;
    private long exitTime = 0;
    private static Activity sFirstInstance;
    private Boolean once = true; // 是否是第一次弹出

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

        mAction11Fragment = new Action11Fragment();
        mEvaluationFragment = new EvaluationNewFragment();
        mHomeFragment = new HomeNewFragment();
        mTryFragment = new FreeNewFragment();
        mMineFragment = new MineFragment();
        mFragments.add(mAction11Fragment);
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
//        if (SPUtils.getInstance().getBoolean(CommonDate.FIRST, true)) {
//            //新人第一次进入app
//            mGuildBackground.setVisibility(View.VISIBLE);
//        } else {
            //老人进入app
            mGuildBackground.setVisibility(View.GONE);
            mPresenter.getAppVersion(this.bindToLifecycle()); //版本更新
//        }
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
                if (bean.getData().getData().getTargetType().equals("5")){
                    if (!bean.getData().getPopId().equals(SPUtils.getInstance().getString(CommonDate.POPID,""))){
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
                            SPUtils.getInstance().put(CommonDate.POPID,bean.getData().getPopId());//未登录时存上id
                        }
                    }else {
                        getClipText();
                    }
                }else {
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
        }else {
            getClipText();
        }
        if (once){
            once = false;
        }
    }

    @Override
    public void onDialogFile() {
        getClipText();
        if (once){
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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (!once){
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

    @OnClick({R.id.guide_break, R.id.guide_next1, R.id.guide_next2, R.id.guide_next3, R.id.guild_background})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guild_background:
                break;
            case R.id.guide_break:
                mGuildBackground.setVisibility(View.GONE);
                mPresenter.getAppVersion(this.bindToLifecycle()); //版本更新
                break;
            case R.id.guide_next1:
                mGuildBackground2.setVisibility(View.GONE);
                mGuildBackground.setBackgroundResource(R.mipmap.guide_bg21);
                mGuideContent2.setImageResource(R.mipmap.guide_content21);
                mGuideContent1.setVisibility(View.GONE);
                mGuideNext1.setVisibility(View.GONE);
                mGuideContent2.setVisibility(View.VISIBLE);
                mGuideNext2.setVisibility(View.VISIBLE);
                mGuideContent3.setVisibility(View.GONE);
                mGuideNext3.setVisibility(View.GONE);
                break;
            case R.id.guide_next2:
                mGuildBackground.setBackgroundResource(R.mipmap.guide_bg31);
                mGuideContent3.setImageResource(R.mipmap.guide_content31);
                mGuideContent1.setVisibility(View.GONE);
                mGuideNext1.setVisibility(View.GONE);
                mGuideContent2.setVisibility(View.GONE);
                mGuideNext2.setVisibility(View.GONE);
                mGuideContent3.setVisibility(View.VISIBLE);
                mGuideNext3.setVisibility(View.VISIBLE);
                break;
            case R.id.guide_next3://下一步
                mGuildBackground.setVisibility(View.GONE);
                mPresenter.getAppVersion(this.bindToLifecycle()); //版本更新
                break;
        }
    }

    public void getClipText(){
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData  = cm.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            ClipData.Item item = clipData .getItemAt(0);
            String content = item.getText().toString();
            if (!TextUtils.isEmpty(content) && !content.equals(SPUtils.getInstance().getString(CommonDate.CLIP,""))){
                mPresenter.getGoodsByTkl(content,this.bindToLifecycle());
            }
        }
    }

    //淘口令返回
    @Override
    public void onTklDialog(TklBean bean,String tkl) {
        DialogUtil.tklDialog(this,bean,tkl);
        SPUtils.getInstance().put(CommonDate.CLIP,tkl);
    }

}
