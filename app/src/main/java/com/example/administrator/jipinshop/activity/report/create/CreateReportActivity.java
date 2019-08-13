package com.example.administrator.jipinshop.activity.report.create;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.report.cover.CoverReportActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.ReportBean;
import com.example.administrator.jipinshop.bean.ReportContentBean;
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.databinding.ActivityCreateReportBinding;
import com.example.administrator.jipinshop.util.ImageCompressUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.SelectPicDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/5/21
 * @Describe 书写试用报告
 */
public class CreateReportActivity extends BaseActivity implements View.OnClickListener, CreateReportView, SelectPicDialog.ChoosePhotoCallback {

    @Inject
    CreateReportPresenter mPresenter;
    private ActivityCreateReportBinding mBinding;
    private List<ReportContentBean> mList;
    private SelectPicDialog mDialog;
    private String Conver = "";//封面

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_create_report);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        initView();
    }

    private void initView() {
        mList = new ArrayList<>();
        mPresenter.setView(this);
        mPresenter.myReportInfo(getIntent().getStringExtra("trialId"),this.bindToLifecycle());
        mBinding.inClude.titleTv.setText("试用报告");
        mBinding.reportTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(36)});
        mBinding.reportTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    mBinding.reportTitleLimi.setText("0/36");
                }else {
                    String html = "<font color='#202020'>"+ s.length() + "</font>/36";
                    mBinding.reportTitleLimi.setText(Html.fromHtml(html));
                }
            }
        });
        //解决ScrollView中包含EditText在启动的时候滚动
        mBinding.scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        mBinding.scrollView.setFocusable(true);
        mBinding.scrollView.setFocusableInTouchMode(true);
        mBinding.scrollView.setOnTouchListener((v, event) -> {
            v.requestFocusFromTouch();
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.report_next:
                //下一步
                if (TextUtils.isEmpty(mBinding.reportTitle.getText().toString())){
                    ToastUtil.show("标题不能为空");
                    return;
                }
                List<TryDetailBean.DataBean.GoodsContentListBean> dataBeans = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    dataBeans.add(mList.get(i).getDataBean());
                }
                int textNum = 0;int imgNum = 0;
                for (TryDetailBean.DataBean.GoodsContentListBean dataBean : dataBeans) {
                    if (dataBean.getType().equals("1")) {
                        textNum = textNum + dataBean.getValue().length();
                    }else {
                        imgNum++;
                    }
                }
                if (textNum <= 500){
                    ToastUtil.show("正文文字不得小于500字");
                    return;
                }
                if (imgNum < 5){
                    ToastUtil.show("正文图片不得小于5张 ");
                    return;
                }
                String json = new Gson().toJson(dataBeans,new TypeToken<List<TryDetailBean.DataBean.GoodsContentListBean>>(){}.getType());
                startActivityForResult(new Intent(this, CoverReportActivity.class)
                        .putExtra("content",json)
                        .putExtra("title",mBinding.reportTitle.getText().toString())
                        .putExtra("trialId",getIntent().getStringExtra("trialId"))
                        .putExtra("conver",Conver)
                ,331);
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.report_text:
                //添加文字
                if (mList.get(mList.size() - 1).getDataBean().getType().equals("2")){
                    mPresenter.addText(this,mBinding.reportContentContainer,"",mList);
                    mBinding.scrollView.post(() -> mBinding.scrollView.fullScroll(View.FOCUS_DOWN));
                }else {
                    showKeyboardDelayed(mList.get(mList.size() - 1).getEditText());
                    mList.get(mList.size() - 1).getEditText().setSelection(mList.get(mList.size() - 1).getEditText().getText().length());
                }
                break;
            case R.id.report_pic:
                //添加图片
                mBinding.reportTitle.clearFocus();
                if(mDialog == null){
                    mDialog = new SelectPicDialog();
                    mDialog.setChoosePhotoCallback(this);
                }
                if(!mDialog.isAdded()){
                    mDialog.show(getSupportFragmentManager(), SelectPicDialog.TAG);
                }
                break;
        }
    }

    @Override
    public void onSuccessReport(ReportBean bean) {
        if (bean.getData() != null && !TextUtils.isEmpty(bean.getData().getContent())){
            //提交过
            List<TryDetailBean.DataBean.GoodsContentListBean> dataBeans = new ArrayList<>();
            dataBeans.addAll(new Gson().fromJson(bean.getData().getContent(),new TypeToken<List<TryDetailBean.DataBean.GoodsContentListBean>>(){}.getType()));
            for (TryDetailBean.DataBean.GoodsContentListBean dataBean : dataBeans) {
                if (dataBean.getType().equals("1")){
                    mPresenter.addText(this,mBinding.reportContentContainer,dataBean.getValue(),mList);
                }else {
                    mPresenter.addImge(this,mBinding.reportContentContainer,dataBean.getValue(),mList,dataBean.getWidth(),dataBean.getHeight(),null);
                }
            }
            mBinding.reportTitle.setText(bean.getData().getTitle());
            mBinding.reportTitle.setSelection(mBinding.reportTitle.getText().length());
            mBinding.reportTitle.clearFocus();
            Conver = bean.getData().getImg();
            String html = "<font color='#202020'>"+ mBinding.reportTitle.getText().length() + "</font>/36";
            mBinding.reportTitleLimi.setText(Html.fromHtml(html));
        }else {
            //没有提交过报告
            mPresenter.addText(this,mBinding.reportContentContainer,"",mList);
            showKeyboardDelayed(mBinding.reportTitle);
        }
    }

    @Override
    public void onFileReport(String error) {
        ToastUtil.show(error);
        mPresenter.addText(this,mBinding.reportContentContainer,"",mList);
        showKeyboardDelayed(mBinding.reportTitle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                showKeyboard(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void getAbsolutePicPath(String picFile) {
        Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(this, "请求中...");
        mDialog.show();
        //这里进行了图片旋转后得到新图片
        picFile = ImageCompressUtil.getimage1(this,ImageCompressUtil.getPicture(this,picFile));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picFile, options);
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        mPresenter.importCustomer(this.bindToLifecycle(),mDialog,new File(picFile),imgWidth,imgHeight);
    }

    @Override
    public void uploadPicSuccess(String picPath,int imgWidth,int imgHeight, File file) {
        if (mList.size() == 1 && mList.get(0).getDataBean().getType().equals("1")
                && TextUtils.isEmpty(mList.get(0).getDataBean().getValue())){
            //判断一进来就选择图片时的情况，删掉第一个editText
            mList.get(0).getEditText().setVisibility(View.GONE);
            mList.remove(0);
        }
        mPresenter.addImge(this,mBinding.reportContentContainer,picPath,mList,imgWidth,imgHeight,file);
        mBinding.scrollView.post(() -> mBinding.scrollView.fullScroll(View.FOCUS_DOWN));
    }

    @Override
    public void uploadPicFailed(String error) {
        ToastUtil.show(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 331:
                setResult(334);
                finish();
                break;
        }
    }
}
