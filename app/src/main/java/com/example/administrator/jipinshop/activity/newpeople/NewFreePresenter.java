package com.example.administrator.jipinshop.activity.newpeople;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/5/22
 * @Describe
 */
public class NewFreePresenter {

    private Repository mRepository;
    private NewFreeView mView;

    public void setView(NewFreeView view) {
        mView = view;
    }

    @Inject
    public NewFreePresenter(Repository repository) {
        mRepository = repository;
    }


    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                mSwipeToLoad.setRefreshEnabled(linearManager.findFirstCompletelyVisibleItemPosition() == 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void adFlipper(Context context , ViewFlipper viewFlipper , List<MemberNewBean.DataBean.MessageListBean> adList){
        viewFlipper.removeAllViews();
        for (int i = 0; i < adList.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_flipper2,null);
            ImageView item_image = view.findViewById(R.id.item_image);
            TextView item_name = view.findViewById(R.id.item_name);
            TextView item_time = view.findViewById(R.id.item_time);
            item_name.setText(adList.get(i).getContent());
            item_time.setText(adList.get(i).getTime());
            GlideApp.loderCircleImage(context,adList.get(i).getAvatar(),item_image,0,0);
            viewFlipper.addView(view);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();
    }

    public void getData(LifecycleTransformer<NewFreeBean> transformer){
        mRepository.freeList2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onSuccess(bean);
                    }else {
                        mView.onFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }

    public void freeGetIndexPosterImg(SHARE_MEDIA share_media , LifecycleTransformer<ImageBean> transformer){
        mRepository.freeGetIndexPosterImg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        mView.onShareSuc(bean,share_media);
                    }else {
                        mView.onCommenFile(bean.getMsg());
                    }
                }, throwable -> {
                    mView.onCommenFile(throwable.getMessage());
                });
    }

    //分享免单
    public void taskFinish(LifecycleTransformer<TaskFinishBean> transformer){
        mRepository.taskFinish("22")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(taskFinishBean -> {
                }, throwable ->{});
    }

}
