package com.example.administrator.jipinshop.activity.info.editname;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
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
 * @create 2018/8/7
 * @Describe 编辑名字
 */
public class EditNameActivity extends BaseActivity implements EditNameView {

    public static final String tag = "editUser";

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.edit_save)
    TextView mEditSave;
    @BindView(R.id.edit_close)
    ImageView mEditClose;
    @BindView(R.id.edit_edit)
    EditText mEditEdit;

    @Inject
    EditNamePresent mPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editname);
        mButterKnife = ButterKnife.bind(this);
        mBaseActivityComponent.inject(this);
        mPresent.setView(this);
        mTitleTv.setText(getIntent().getStringExtra("title"));
        mEditEdit.setHint("请输入您的名字");
        mEditEdit.setText(getIntent().getStringExtra("value"));
        mEditEdit.setSelection(mEditEdit.getText().length());//将光标移至文字末尾
    }

    @OnClick({R.id.title_back, R.id.edit_save, R.id.edit_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                showKeyboard(false);
                finish();
                break;
            case R.id.edit_save:
                if(TextUtils.isEmpty(mEditEdit.getText().toString())){
                    ToastUtil.show("请输入修改的名字");
                    return;
                }
                showKeyboard(false);
                Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
                mDialog.show();
                mPresent.SaveUserInfo(mEditEdit.getText().toString(), this.bindToLifecycle(), mDialog);
                break;
            case R.id.edit_close:
                mEditEdit.setText("");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mButterKnife.unbind();
        super.onDestroy();
    }

    /**
     * 保存结果
     *
     * @param successBean
     */
    @Override
    public void Success(SuccessBean successBean) {
        if (successBean.getCode() == 0) {
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickName, mEditEdit.getText().toString());
            EventBus.getDefault().post(new EditNameBus(EditNameActivity.tag, mEditEdit.getText().toString(),"1"));
            ToastUtil.show("修改成功");
            finish();
        } else {
            ToastUtil.show(successBean.getMsg());
        }
    }
}
