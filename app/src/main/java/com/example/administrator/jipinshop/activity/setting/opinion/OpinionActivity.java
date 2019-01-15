package com.example.administrator.jipinshop.activity.setting.opinion;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe 我要反馈
 */
public class OpinionActivity extends BaseActivity implements OpinionView {


    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.opinion_edit)
    EditText mOpinionEdit;
    @BindView(R.id.opinion_text)
    TextView mOpinionText;
    @BindView(R.id.opinion_submit)
    TextView mOpinionSubmit;

    @Inject
    OpinionPresenter mPresenter;

    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("我要反馈");
        String html = "您还可以输入<font color='#E31436' size='20'>"+200+"</font>个字";
        mOpinionText.setText(Html.fromHtml(html));
        mOpinionEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        mPresenter.initText(mOpinionEdit,mOpinionText);

        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mPresenter.setView(this);
    }

    @OnClick({R.id.title_back, R.id.opinion_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.opinion_submit:
                if(mDialog != null){
                    mDialog.show();
                }
                mPresenter.feedBook(mOpinionEdit.getText().toString(),this.bindToLifecycle());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }

    /**
     * 提交反馈回调
     * @param successBean
     */
    @Override
    public void OpinionSuccess(SuccessBean successBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        if(successBean.getCode() == 0){
            ToastUtil.show("提交成功");
            finish();
        }else {
            ToastUtil.show( successBean.getMsg());
        }
    }
}
