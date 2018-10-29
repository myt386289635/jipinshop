package com.example.administrator.jipinshop.activity.sreach.result;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SreachResultBean;
import com.example.administrator.jipinshop.bean.SreachTagBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe
 */
public class SreachResultPresenter {

    private Repository mRepository;

    private SreachResultView mView;

    public void setView(SreachResultView view) {
        mView = view;
    }

    @Inject
    public SreachResultPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initHot(final Context context, final FlexboxLayout flexboxLayout,List<String> hotText) {
        for (int i = 0; i < hotText.size(); i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
            textView.setText(hotText.get(i));
            delete.setVisibility(View.GONE);
            textView.setOnClickListener(v -> {
                if(mView != null){
                    mView.onResher("2",textView.getText().toString());
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
    }

    public void initHistroy(final Context context, final FlexboxLayout flexboxLayout,
                            final List<ImageView> deteles,List<SreachTagBean> histroyText,
                            List<View> histroyFlex) {
        for (int i = 0; i < histroyText.size(); i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
            SreachTagBean bean = histroyText.get(i);
            textView.setText(bean.getName());
            deteles.add(delete);
            textView.setOnLongClickListener(view -> {
                for (int j = 0; j < deteles.size(); j++) {
                    deteles.get(j).setVisibility(View.GONE);
                }
                delete.setVisibility(View.VISIBLE);
                return true;
            });
            delete.setOnClickListener(view -> {
                flexboxLayout.removeView(itemTypeView);
                histroyFlex.remove(itemTypeView);
                deteles.remove(delete);
                histroyText.remove(bean);
                String str = new Gson().toJson(histroyText);
                SPUtils.getInstance(CommonDate.USER).put(CommonDate.historySreach,str);
            });
            textView.setOnClickListener(v -> {
                if(mView != null){
                    mView.onResher("1",textView.getText().toString());
                }
            });
            flexboxLayout.addView(itemTypeView);
            histroyFlex.add(itemTypeView);
        }
    }


    public void addSearchFlex(String content, Context context, final FlexboxLayout flexboxLayout,
                              final List<ImageView> deteles,List<SreachTagBean> histroyText,
                              List<View> histroyFlex){

        boolean tag = false;
        int pos = 0;
        for (int i = 0; i < histroyText.size(); i++) {
            if(histroyText.get(i).getName().equals(content)){
                tag = true;
                pos = i;
                break;
            }
        }
        if(tag){
            histroyText.remove(pos);
            View view = histroyFlex.get(pos);
            flexboxLayout.removeView(view);
            histroyFlex.remove(view);
        }

        final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
        TextView textView = itemTypeView.findViewById(R.id.histroy_item);
        final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
        textView.setText(content);
        SreachTagBean bean = new SreachTagBean(content);
        histroyText.add(0,bean);
        delete.setVisibility(View.GONE);
        textView.setOnLongClickListener(view -> {
            for (int j = 0; j < deteles.size(); j++) {
                deteles.get(j).setVisibility(View.GONE);
            }
            delete.setVisibility(View.VISIBLE);
            return true;
        });
        delete.setOnClickListener(view -> {
            flexboxLayout.removeView(itemTypeView);
            histroyFlex.remove(itemTypeView);
            deteles.remove(delete);
            histroyText.remove(bean);
            String str = new Gson().toJson(histroyText);
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.historySreach,str);
        });
        textView.setOnClickListener(v -> {
            if(mView != null){
                mView.onResher("1",textView.getText().toString());
            }
        });
        deteles.add(0,delete);
        flexboxLayout.addView(itemTypeView,0);
        histroyFlex.add(0,itemTypeView);

        if (histroyText.size() >= 10){
            for (int i = 10; i < histroyText.size(); i++) {
                View view = histroyFlex.get(i);
                flexboxLayout.removeView(view);
                histroyText.remove(i);
            }
        }

    }

    //解决冲突问题
    public void solveScoll(RecyclerView mRecyclerView, final SwipeToLoadLayout mSwipeToLoad){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeToLoad.setRefreshEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    ImageManager.getImageLoader().resume();//为了在滑动时不卡顿
                }else {
                    ImageManager.getImageLoader().pause();//为了在滑动时不卡顿
                }
            }
        });
    }

    public void searchGoods(String goodsName,LifecycleTransformer<SreachResultBean> transformer){
        mRepository.searchGoods(goodsName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(sreachResultBean -> {
                    if (sreachResultBean.getCode() == 200){
                        if(mView != null){
                            mView.Success(sreachResultBean);
                        }
                    }else {
                        if(mView != null){
                            mView.Faile(sreachResultBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if(mView != null){
                        mView.Faile(throwable.getMessage());
                    }
                });
    }

}
