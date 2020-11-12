package com.example.administrator.jipinshop.activity.sreach.play;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.google.android.flexbox.FlexboxLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2020/11/11
 * @Describe
 */
public class PlaySreachPresenter {

    private Repository mRepository;
    private PlaySreachView mView;

    public void setView(PlaySreachView view) {
        mView = view;
    }

    @Inject
    public PlaySreachPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initHot(final Context context, final FlexboxLayout flexboxLayout, List<SreachHistoryBean.DataBean.HotWordListBean> hotText) {
        for (int i = 0; i < hotText.size(); i++) {
            final View itemTypeView = LayoutInflater.from(context).inflate(R.layout.item_sreach_histroy, null);
            final TextView textView = itemTypeView.findViewById(R.id.histroy_item);
            final ImageView delete = itemTypeView.findViewById(R.id.histroy_delete);
            textView.setText(hotText.get(i).getWord());
            delete.setVisibility(View.GONE);
            int finalI = i;
            textView.setOnClickListener(v -> {
                if(mView != null){
                    mView.jump(textView.getText().toString(), finalI);
                }
            });
            flexboxLayout.addView(itemTypeView);
        }
    }

    /**
     * 获取搜索记录
     */
    public void searchLog(LifecycleTransformer<SreachHistoryBean> transformer){
        mRepository.searchLog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(sreachHistoryBean -> {
                    if(sreachHistoryBean.getCode() == 0){
                        mView.Success(sreachHistoryBean);
                    }else {
                        mView.onFaile(sreachHistoryBean.getMsg());
                    }
                }, throwable -> {
                    mView.onFaile(throwable.getMessage());
                });
    }
}
