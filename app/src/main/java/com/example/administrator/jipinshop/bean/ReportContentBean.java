package com.example.administrator.jipinshop.bean;

import android.widget.EditText;
import android.widget.ImageView;

/**
 * @author 莫小婷
 * @create 2019/5/22
 * @Describe
 */
public class ReportContentBean {

    private TryDetailBean.DataBean.GoodsContentListBean mDataBean;
    private EditText mEditText;
    private ImageView mImageView;

    public ReportContentBean(TryDetailBean.DataBean.GoodsContentListBean dataBean) {
        mDataBean = dataBean;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    public TryDetailBean.DataBean.GoodsContentListBean getDataBean() {
        return mDataBean;
    }

    public void setDataBean(TryDetailBean.DataBean.GoodsContentListBean dataBean) {
        mDataBean = dataBean;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public void setEditText(EditText editText) {
        mEditText = editText;
    }
}
