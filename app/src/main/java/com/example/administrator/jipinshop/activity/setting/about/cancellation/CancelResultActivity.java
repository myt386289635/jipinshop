package com.example.administrator.jipinshop.activity.setting.about.cancellation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.databinding.ActivityCancelResultBinding;

/**
 * @author 莫小婷
 * @create 2020/5/18
 * @Describe 注销账号结果
 */
public class CancelResultActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCancelResultBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_cancel_result);
        mBinding.setListener(this);
        mBinding.inClude.titleTv.setText("注销账号");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
            case R.id.cancel_result:
                finish();
                break;
        }
    }
}
