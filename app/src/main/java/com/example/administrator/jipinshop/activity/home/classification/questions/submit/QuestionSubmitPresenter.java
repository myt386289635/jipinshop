package com.example.administrator.jipinshop.activity.home.classification.questions.submit;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.databinding.ActivityQuestionSubmitBinding;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2019/7/8
 * @Describe
 */
public class QuestionSubmitPresenter {

    private Repository mRepository;
    private QuestionSubmitView mView;

    public void setView(QuestionSubmitView view) {
        mView = view;
    }

    @Inject
    public QuestionSubmitPresenter(Repository repository) {
        mRepository = repository;
    }

    public void initPager(Context context,ActivityQuestionSubmitBinding binding){
        binding.questionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)){
                    binding.questionSubmit.setTextColor(context.getResources().getColor(R.color.color_E84643));
                    String html = "<font color='#202020'>"+s.length()+"</font>/36";
                    binding.questionText.setText(Html.fromHtml(html));
                }else {
                    binding.questionSubmit.setTextColor(context.getResources().getColor(R.color.color_D8D8D8));
                    binding.questionText.setText("0/36");
                }
            }
        });
    }

    public void addQuestion(String goodsCategoryId , String title , LifecycleTransformer<SuccessBean> transformer){
        mRepository.addQuestion(goodsCategoryId, title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if (successBean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccess();
                        }
                    }else {
                        if (mView != null){
                            mView.onFile(successBean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFile(throwable.getMessage());
                    }
                });
    }
}
