package com.example.administrator.jipinshop.fragment.index;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentIndexVideoBinding;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * @author 莫小婷
 * @create 2020/4/17
 * @Describe 引导页视频
 */
public class IndexVideoFragment extends DBBaseFragment implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    @Inject
    AppStatisticalUtil appStatisticalUtil;
    private FragmentIndexVideoBinding mBinding;
    private boolean isPlay = false; //是否播放完毕  默认为否
    private int downTimer = 13;
    private CountDownTimer timer;

    public static IndexVideoFragment getInstence() {
        IndexVideoFragment fragment = new IndexVideoFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_index_video, container, false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        setStatusBarHight(mBinding.statusBar, getContext());
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.jipinshop;
        mBinding.indexVideo.setVideoPath(uri);
        mBinding.indexVideo.setOnCompletionListener(this);
        mBinding.indexVideo.setOnPreparedListener(this);
        Glide.with(this)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(100000)
                )
                .load(uri)
                .into(mBinding.indexVideoImage);
        mBinding.indexVideoImage.setVisibility(View.VISIBLE);
        if (!mBinding.indexVideo.isPlaying() && !isPlay) {
            mBinding.indexVideo.start();
            startTimer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopPlaybackVideo();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void stopPlaybackVideo() {
        try {
            mBinding.indexVideo.stopPlayback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStatusBarHight(LinearLayout StatusBar, Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_reStart:
                appStatisticalUtil.addEvent("yindao2_chongbo",this.bindToLifecycle());
                if (!mBinding.indexVideo.isPlaying()) {
                    isPlay = false;
                    downTimer = 13;
                    mBinding.indexTime.setText(downTimer + "s");
                    mBinding.indexVideo.start();
                    startTimer();
                } else {
                    ToastUtil.show("视频正在播放中，请观看");
                }
                break;
            case R.id.index_goto:
                appStatisticalUtil.addEvent("yindao2_tiyan",this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
                startActivity(new Intent(getContext(), MainActivity.class));
                if (getActivity() != null) {
                    getActivity().finish();
                    if (mBinding.indexVideo.canPause()) {
                        mBinding.indexVideo.pause();
                    }
                }
                break;
        }
    }

    //播放完成后的工作
    @Override
    public void onCompletion(MediaPlayer mp) {
        isPlay = true;
    }

    public void startTimer(){
        mBinding.indexVideoImage.setVisibility(View.INVISIBLE);
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(downTimer * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                downTimer = (int) (millisUntilFinished / 1000);
                mBinding.indexTime.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() { }
        };
        timer.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
