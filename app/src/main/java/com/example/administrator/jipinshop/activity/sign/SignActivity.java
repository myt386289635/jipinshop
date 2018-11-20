package com.example.administrator.jipinshop.activity.sign;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.LuckImageBean;
import com.example.administrator.jipinshop.bean.LuckselectBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SupplementBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.ActivitySignBinding;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/23
 * @Describe 签到页面
 */
public class SignActivity extends BaseActivity implements View.OnClickListener, SignView {

    public static final String eventbusTag = "SignActivity";

    @Inject
    SignPresenter mPresenter;

    private ActivitySignBinding mBinding;

    private List<View> views = new LinkedList<>();//所有的视图
    private int timeC = 100;//变色时间间隔
    private int lightPosition = 0;//当前亮灯位置,从0开始
    private int runCount = 5;//需要转多少圈
    private int lunckyPosition = 0;//中奖的幸运位置,从0开始
    private int last = 0;//记录之前的位置
    private Boolean exit = false;//记录最后一圈是否已经走到正确位置

    private CountDownTimer mTimer;
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (mTimer != null) {
                mTimer.cancel();
            }
            mBinding.start.setClickable(true);
            mBinding.start.setEnabled(true);

            if(TextUtils.isEmpty(LuckMark) && TextUtils.isEmpty(LuckString)){
                DialogUtil.SignPrice(SignActivity.this, LuckString,"很抱歉您没有抽到奖品");
            }else {
                DialogUtil.SignPrice(SignActivity.this, LuckString,"恭喜您获得" + LuckMark);
            }
            last = lunckyPosition;
            if (lunckyPosition != 7) {
//                if (lunckyPosition != views.size())
                views.get(7).setVisibility(View.GONE);
            }
            EventBus.getDefault().post(new EditNameBus(SignActivity.eventbusTag));
            return true;
        }
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());

    private List<TextView> mTextViews = new ArrayList<>();
    private Dialog mDialog;

    /**
     * 控制今天只能签到一次 没签到：false  签到了：true
     */
    private Boolean signFlag = false;
    /**
     * 记录抽中的奖品头像
     */
    private String LuckString = "";
    /**
     * 记录抽中的奖品名字
     */
    private String LuckMark = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setSignView(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("签到");

        views.add(mBinding.prize1Bg);
        views.add(mBinding.prize2Bg);
        views.add(mBinding.prize3Bg);
        views.add(mBinding.prize4Bg);
        views.add(mBinding.prize5Bg);
        views.add(mBinding.prize6Bg);
        views.add(mBinding.prize7Bg);
        views.add(mBinding.prize8Bg);

        /*初始化数据*/
        mTextViews.add(mBinding.signMon);
        mTextViews.add(mBinding.sighTue);
        mTextViews.add(mBinding.signWed);
        mTextViews.add(mBinding.signThu);
        mTextViews.add(mBinding.signFri);
        mTextViews.add(mBinding.signSat);
        mTextViews.add(mBinding.signSun);

        int day = getWeek();
        for (int i = 0; i < day - 1; i++) {
            upImg(R.mipmap.h5_signin_supplement, mTextViews.get(i));
        }
        for (int i = day - 1; i < 7; i++) {
            upImg(R.mipmap.h5_signin_unsigned, mTextViews.get(i));
        }

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mPresenter.signInfo(this.bindToLifecycle());
        mPresenter.luckImage(this.bindToLifecycle());
    }


    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_signup:
                if (signFlag) {
                    Toast.makeText(this, "今天您已经签到过了", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog dialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                dialog.show();
                mPresenter.sign(dialog, this.bindToLifecycle());
                break;
            case R.id.sign_supplement:
                Dialog dialog1 = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                dialog1.show();
                mPresenter.Supplement(dialog1, this.bindToLifecycle());
                break;
            case R.id.start:
                Dialog dialogLuck = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                dialogLuck.show();
                mPresenter.luckselect(dialogLuck, this.bindToLifecycle());
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    /**
     * 获取签到信息成功
     *
     * @param signBean
     */
    @Override
    public void getInfoSuc(SignBean signBean) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (signBean.getSigninInfo().getWeekArray() != null && signBean.getSigninInfo().getWeekArray().size() != 0) {
            int day = getWeek();//今天星期几
            for (int i = 0; i < day; i++) {
                switch (signBean.getSigninInfo().getWeekArray().get(i)) {
                    case "2"://补签
//                        upImg(R.mipmap.h5_signin_supplement, mTextViews.get(i));
                        break;
                    case "1"://已签过
                        if(day == (i + 1)){
                            signFlag = true;
                        }
                        upImg(R.mipmap.h5_signin_complete, mTextViews.get(i));
                        break;
                    case "0"://未签到
//                        upImg(R.mipmap.h5_signin_unsigned, mTextViews.get(i));
                        break;
                }
            }
        }

    }

    /**
     * 获取签到信息失败
     */
    @Override
    public void getInfoFaile(String error) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        Toast.makeText(this, error + ",请重新进入页面", Toast.LENGTH_SHORT).show();
    }

    /**
     * 签到成功反馈
     */
    @Override
    public void signSuc(SignInsertBean signInsertBean) {
        signFlag = true;
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, signInsertBean.getPoints());
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade, signInsertBean.getUserMemberGrade());
        upImg(R.mipmap.h5_signin_complete, mTextViews.get(getWeek() - 1));
        EventBus.getDefault().post(new EditNameBus(SignActivity.eventbusTag));
        DialogUtil.SignSuccess(this, "恭喜您签到成功！", "+"+signInsertBean.getAddPoint()+"积分");
    }

    /**
     * 签到失败反馈
     */
    @Override
    public void signFaile(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 补签成功反馈
     *
     * @param supplementBean
     */
    @Override
    public void SuppleSuc(SupplementBean supplementBean) {
        int day = getWeek();
        for (int i = 0; i < day; i++) {
            upImg(R.mipmap.h5_signin_complete, mTextViews.get(i));
        }
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, Integer.valueOf(supplementBean.getPoints()));
        EventBus.getDefault().post(new EditNameBus(SignActivity.eventbusTag));
        DialogUtil.SignSuccess(this, "恭喜您补签"+supplementBean.getSupplementDays()+"天成功！", supplementBean.getPoint() + "积分");
    }

    /**
     * 补签失败反馈
     *
     * @param error
     */
    @Override
    public void SuppleFaile(String error) {
        if (error.equals("641")){
            DialogUtil.SignFaile(this,R.mipmap.h5_signfail);
        }else {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 抽奖成功
     */
    @Override
    public void LuckSuc(LuckselectBean luckselectBean) {
        LuckString = luckselectBean.getMyPrize().getImgurl();
        LuckMark = luckselectBean.getMyPrize().getName();
        mBinding.start.setClickable(false);
        mBinding.start.setEnabled(false);
        runCount = 5;
        timeC = 100;
        if(luckselectBean.getPoints() != 0){
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, luckselectBean.getPoints());
        }
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade, luckselectBean.getUserMemberGrade());
        lunckyPosition = luckselectBean.getMyPrize().getId();
        views.get(lunckyPosition).setVisibility(View.GONE);
        mTimer = new TimeCount(timeC * 9, timeC);
        mTimer.start();
    }

    /**
     * 抽奖失败
     */
    @Override
    public void LuckFaile(String error) {
        if(error.equals("642")){
            DialogUtil.SignFaile(this,R.mipmap.h5_noway);
        }else {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化抽奖图片成功
     * @param luckImageBean
     */
    @Override
    public void LuckImageSuc(LuckImageBean luckImageBean) {
        ImageManager.displayImage(luckImageBean.getPrizeList().get(0).getImgurl(),mBinding.prize1,0,0);
        ImageManager.displayImage(luckImageBean.getPrizeList().get(1).getImgurl(),mBinding.prize2,0,0);
        ImageManager.displayImage(luckImageBean.getPrizeList().get(2).getImgurl(),mBinding.prize3,0,0);
        ImageManager.displayImage(luckImageBean.getPrizeList().get(3).getImgurl(),mBinding.prize4,0,0);
        ImageManager.displayImage(luckImageBean.getPrizeList().get(4).getImgurl(),mBinding.prize5,0,0);
        ImageManager.displayImage(luckImageBean.getPrizeList().get(5).getImgurl(),mBinding.prize6,0,0);
        ImageManager.displayImage(luckImageBean.getPrizeList().get(6).getImgurl(),mBinding.prize7,0,0);
        ImageManager.displayImage(luckImageBean.getPrizeList().get(7).getImgurl(),mBinding.prize8,0,0);
    }

    /**
     * 初始化抽奖图片失败
     */
    @Override
    public void LuckImageFaile(String error) {
        Toast.makeText(this, error + ",请重新进入页面", Toast.LENGTH_SHORT).show();
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            lightPosition = last;
            exit = false;//没有走到
        }

        @Override
        public void onTick(long millisUntilFinished) {

            Log.i(">>>", "---" + lightPosition);
            //如果是最后一次滚动
            if (runCount > 0) {
                if (lightPosition < 9 && lightPosition > 0) {
                    views.get(lightPosition - 1).setVisibility(View.GONE);
                }
                if (lightPosition < 8) {
                    views.get(lightPosition).setVisibility(View.VISIBLE);
                }
                if (lightPosition == 8) {
                    views.get(0).setVisibility(View.VISIBLE);
                }
            } else if (runCount == 0) {

                if (lightPosition != lunckyPosition) {
                    if (!exit) {
                        if (lightPosition < 9 && lightPosition > 0) {
                            views.get(lightPosition - 1).setVisibility(View.GONE);
                        }
                        if (lightPosition < 8) {
                            views.get(lightPosition).setVisibility(View.VISIBLE);
                        }
                        if (lightPosition == 8) {
                            views.get(0).setVisibility(View.VISIBLE);
                            if (lunckyPosition == 0) {
                                exit = true;
                                mHandler.sendEmptyMessage(100);
                            }
                        }
                    }
                } else {
                    exit = true;
                    if (lightPosition < 9 && lightPosition > 0) {
                        views.get(lightPosition - 1).setVisibility(View.GONE);
                    }
                    if (lightPosition < 8) {
                        views.get(lightPosition).setVisibility(View.VISIBLE);
                    }
                    mHandler.sendEmptyMessage(100);
                }
            }

            lightPosition++;
            if (lightPosition == 9) {
                lightPosition = 1;
            }
        }

        @Override
        public void onFinish() {
            Log.i(">>>", "onFinish==" + runCount);
            //如果不是最后一圈，需要还原最后一块的颜色
            View tvLast = views.get(7);
            if (runCount != 0) {
                if (last != 7) {
                    tvLast.setVisibility(View.GONE);
                }
                //最后几转速度变慢
                if (runCount < 3) timeC += 200;
                mTimer = new TimeCount(timeC * 9, timeC);
                mTimer.start();
                runCount--;
            }
        }
    }


    public void upImg(int id, TextView textView) {
        Drawable bottomDrawable = getResources().getDrawable(id);
        bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, null, bottomDrawable);
    }

    /**
     * 获取今天是星期几
     */
    public int getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                //星期日
                return 7;
            case 2:
                //星期一
                return 1;
            case 3:
                //星期二
                return 2;
            case 4:
                //星期三
                return 3;
            case 5:
                //星期四
                return 4;
            case 6:
                //星期五
                return 5;
            case 7:
                //星期六
                return 6;
        }
        return 1;
    }

}
