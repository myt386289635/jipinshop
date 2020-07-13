package com.example.administrator.jipinshop.activity.setting.opinion;

import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe
 */
public class OpinionPresenter {

    private Repository mRepository;
    private OpinionView mView;

    public void setView(OpinionView view) {
        mView = view;
    }

    @Inject
    public OpinionPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initText(final EditText mOpinionEdit , final TextView mOpinionText){
        mOpinionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String html = "您还可以输入<font color='#E25838' size='20'>"+(200-s.length())+"</font>个字";
                mOpinionText.setText(Html.fromHtml(html));
            }
        });
    }


    public void feedBook(String content,LifecycleTransformer<SuccessBean> transformer){
      mRepository.feedBack(content,"1")
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .compose(transformer)
              .subscribe(successBean -> {
                  if(mView != null){
                      mView.OpinionSuccess(successBean);
                  }
              }, throwable -> {
                  Log.d("OpinionPresenter", "throwable:" + throwable.getMessage());
              });
    }

}
