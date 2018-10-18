package com.example.administrator.jipinshop.activity.balance;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.math.BigDecimal;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 莫小婷
 * @create 2018/8/8
 * @Describe
 */
public class BalancePresenter  {

    Repository mRepository;
    private BalanceView mView;

    public void setView(BalanceView view) {
        mView = view;
    }

    @Inject
    public BalancePresenter(Repository repository) {
        mRepository = repository;
    }

    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void getMoney(Context context,LifecycleTransformer<AccountBean> transformer){
        mRepository.account()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(accountBean -> {
                    if(mView != null){
                        mView.successMoney(accountBean);
                    }
                }, throwable -> {
                    Toast.makeText(context, "佣金金额获取失败，请检查网络", Toast.LENGTH_SHORT).show();
                    Log.d("MinePresenter", throwable.getMessage());
                });
    }

    public void initMoneyEdit(EditText editText , String totleMoney){
        editText.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            if (source.equals(".") && dest.toString().length() == 0) {
                return "0.";
            }
            if (dest.toString().contains(".") && source.equals(".")) {
                return "";
            }
            if (dest.toString().contains(".")) {
                int index = dest.toString().indexOf(".");
                int length = dest.toString().substring(index).length();
                if (length == 3) {
                    return "";
                }
            }
            return null;
        }});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editText.getText().toString())){
                    String tMoney = totleMoney.replace("总佣金¥","");
                    BigDecimal bigDecimal = new BigDecimal(editText.getText().toString());
                    BigDecimal totleBigDecimal = new BigDecimal(tMoney);
                    double money = bigDecimal.doubleValue();
                    double totle = totleBigDecimal.doubleValue();
                    if(money > totle){
                        editText.setText(tMoney);
                        editText.setSelection(tMoney.length());
                    }
                }
            }
        });
    }

    public void lipay(Dialog dialog, String phone, String amount, String real_name, LifecycleTransformer<SuccessBean> transformer){
        mRepository.lipay(phone,amount,real_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if(mView != null){
                        mView.alipySuccess(successBean);
                    }
                },throwable -> {
                    Log.d("BoundAlipayPresenter", throwable.getMessage());
                });
    }
}
