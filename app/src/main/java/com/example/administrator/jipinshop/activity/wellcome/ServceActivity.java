package com.example.administrator.jipinshop.activity.wellcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.wellcome.index.IndexMixActivity;
import com.example.administrator.jipinshop.databinding.ActivityServceBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;

/**
 * @author 莫小婷
 * @create 2019/12/4
 * @Describe 相关权限获取页面
 */
public class ServceActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityServceBinding mBinding;
    private Boolean[] mBooleans = {true,true,true};
    private long exitTime = 0;
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_servce);
        mBinding.setListener(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.color_F5F5F5)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0f)
                .init();
        String str = "请在使用极品城前查看并同意完整的<font color='#4A90E2'>《隐私政策》</font>";
        mBinding.sercveNotice.setText(Html.fromHtml(str));
        String str1 = "和<font color='#4A90E2'>《用户协议》</font>";
        mBinding.sercveProtocol.setText(Html.fromHtml(str1));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.servce_go:
                //进入app
                for (int i = 0; i < mBooleans.length; i++) {
                    if (!mBooleans[i]){
                        return;
                    }
                }
                startActivity(new Intent(this, IndexMixActivity.class));
                finish();
                break;
            case R.id.servce1_agree:
                if (mBooleans[0]){
                    mBooleans[0] = false;
                    mBinding.servce1Agree.setImageResource(R.mipmap.bg_servce_no);
                }else {
                    mBooleans[0] = true;
                    mBinding.servce1Agree.setImageResource(R.mipmap.bg_servce_yes);
                }
                initButton();
                break;
            case R.id.servce2_agree:
                if (mBooleans[1]){
                    mBooleans[1] = false;
                    mBinding.servce2Agree.setImageResource(R.mipmap.bg_servce_no);
                }else {
                    mBooleans[1] = true;
                    mBinding.servce2Agree.setImageResource(R.mipmap.bg_servce_yes);
                }
                initButton();
                break;
            case R.id.servce3_agree:
                if (mBooleans[2]){
                    mBooleans[2] = false;
                    mBinding.servce3Agree.setImageResource(R.mipmap.bg_servce_no);
                }else {
                    mBooleans[2] = true;
                    mBinding.servce3Agree.setImageResource(R.mipmap.bg_servce_yes);
                }
                initButton();
                break;
            case R.id.sercve_notice:
                //隐私政策
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"privacy.html")
                        .putExtra(WebActivity.title,"隐私政策")
                );
                break;
            case R.id.sercve_protocol:
                //用户协议
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"agreement.html")
                        .putExtra(WebActivity.title,"用户协议")
                );
                break;
        }
    }

    public void initButton(){
        for (int i = 0; i < mBooleans.length; i++) {
            if (!mBooleans[i]){
                mBinding.servceGo.setBackgroundResource(R.drawable.bg_tab_eva4);
                return;
            }
        }
        mBinding.servceGo.setBackgroundResource(R.drawable.bg_tab_eva);
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
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy(); //必须调用该方法，防止内存泄漏
    }
}
