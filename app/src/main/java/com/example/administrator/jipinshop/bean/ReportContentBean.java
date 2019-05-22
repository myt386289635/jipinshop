package com.example.administrator.jipinshop.bean;

import android.widget.EditText;
import android.widget.ImageView;

/**
 * @author 莫小婷
 * @create 2019/5/22
 * @Describe
 */
public class ReportContentBean {

    private ReportBean.DataBean mDataBean;
    private EditText mEditText;
    private ImageView mImageView;

    public ReportContentBean(ReportBean.DataBean dataBean) {
        mDataBean = dataBean;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    public ReportBean.DataBean getDataBean() {
        return mDataBean;
    }

    public void setDataBean(ReportBean.DataBean dataBean) {
        mDataBean = dataBean;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public void setEditText(EditText editText) {
        mEditText = editText;
    }
}
