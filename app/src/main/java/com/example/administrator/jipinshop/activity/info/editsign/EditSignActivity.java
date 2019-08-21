package com.example.administrator.jipinshop.activity.info.editsign;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameView;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 莫小婷
 * @create 2019/8/21
 * @Describe 编辑个性签名
 */
public class EditSignActivity extends BaseActivity implements TextWatcher, EditNameView {

    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.edit_sign)
    EditText mEditSign;
    @BindView(R.id.edit_edit)
    TextView mEditEdit;

    @Inject
    EditSignPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editsign);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("个性签名");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("value"))){
            mEditSign.setText(getIntent().getStringExtra("value"));
            mEditSign.setSelection(mEditSign.getText().length());//将光标移至文字末尾
            String html = "<font color='#202020'>"+ mEditSign.getText().length() + "</font>/36";
            mEditEdit.setText(Html.fromHtml(html));
        }
        mEditSign.setFilters(new InputFilter[]{new InputFilter.LengthFilter(36)});
        mEditSign.addTextChangedListener(this);
    }

    @OnClick({R.id.title_back, R.id.edit_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.edit_save:
                //保存
                if(TextUtils.isEmpty(mEditSign.getText().toString())){
                    ToastUtil.show("请输入个性签名");
                    return;
                }
                showKeyboard(false);
                Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
                mDialog.show();
                mPresenter.SaveUserInfo(mEditSign.getText().toString(), this.bindToLifecycle(), mDialog);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mButterKnife.unbind();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0){
            mEditEdit.setText("0/36");
        }else {
            String html = "<font color='#202020'>"+ s.length() + "</font>/36";
            mEditEdit.setText(Html.fromHtml(html));
        }
    }

    @Override
    public void Success(SuccessBean successBean) {
        if (successBean.getCode() == 0) {
            EventBus.getDefault().post(new EditNameBus(EditNameActivity.tag, mEditSign.getText().toString(),"6"));
            ToastUtil.show("修改成功");
            finish();
        } else {
            ToastUtil.show(successBean.getMsg());
        }
    }
}
