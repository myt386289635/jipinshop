package com.example.administrator.jipinshop.activity.report.create;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.ReportBean;
import com.example.administrator.jipinshop.bean.ReportContentBean;
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author 莫小婷
 * @create 2019/5/22
 * @Describe
 */
public class CreateReportPresenter {

    private Repository mRepository;
    private CreateReportView mView;

    public void setView(CreateReportView view) {
        mView = view;
    }

    @Inject
    public CreateReportPresenter(Repository repository) {
        mRepository = repository;
    }

    public void myReportInfo(String trialId, LifecycleTransformer<ReportBean> transformer){
        mRepository.myReportInfo(trialId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(bean -> {
                    if (bean.getCode() == 0){
                        if (mView != null){
                            mView.onSuccessReport(bean);
                        }
                    }else {
                        if (mView != null){
                            mView.onFileReport(bean.getMsg());
                        }
                    }
                }, throwable -> {
                    if (mView != null){
                        mView.onFileReport(throwable.getMessage());
                    }
                });
    }

    public void addText(Context context ,LinearLayout linearLayout, String content,List<ReportContentBean> mList){
        TryDetailBean.DataBean.GoodsContentListBean dataBean = new TryDetailBean.DataBean.GoodsContentListBean();
        dataBean.setType("1");
        dataBean.setValue("");
        dataBean.setHeight(0);
        dataBean.setWidth(0);
        View view = LayoutInflater.from(context).inflate(R.layout.item_report_text, null);
        EditText editText = view.findViewById(R.id.report_editText);
        if (!TextUtils.isEmpty(content)){
            editText.setText(content);
            dataBean.setValue(content);
            editText.setSelection(editText.getText().length());
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dataBean.setValue(editText.getText().toString());
            }
        });
        linearLayout.addView(view);
        ReportContentBean bean = new ReportContentBean(dataBean);
        bean.setEditText(editText);
        mList.add(bean);
    }

    public void addImge(Context context ,LinearLayout linearLayout, String img,
                        List<ReportContentBean> mList, double imgWidth,double imgHeight,
                        File file){
        TryDetailBean.DataBean.GoodsContentListBean dataBean = new TryDetailBean.DataBean.GoodsContentListBean();
        dataBean.setType("2");
        dataBean.setValue(img);
        dataBean.setHeight(imgHeight);
        dataBean.setWidth(imgWidth);
        View view = LayoutInflater.from(context).inflate(R.layout.item_report_img, null);
        ImageView imageView = view.findViewById(R.id.report_img);
        RelativeLayout container = view.findViewById(R.id.report_imgContainer);
        ImageView imageViewClose = view.findViewById(R.id.report_imgClose);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = (int) (DistanceHelper.getAndroiodScreenwidthPixels(context) - context.getResources().getDimension(R.dimen.x28) - context.getResources().getDimension(R.dimen.x28));
        layoutParams.height = (int) (imgHeight * layoutParams.width / imgWidth);
        imageView.setLayoutParams(layoutParams);
        if (file == null){
            GlideApp.loderImage(context,img,imageView,0,0);
        }else {
            Glide.with(context)
                    .load(file)
                    .into(imageView);
        }
        linearLayout.addView(view);
        ReportContentBean bean = new ReportContentBean(dataBean);
        bean.setImageView(imageView);
        imageViewClose.setOnClickListener(v ->{
            container.setVisibility(View.GONE);
            mList.remove(bean);
        });
        mList.add(bean);
    }


    /**
     * 上传图片
     */
    public void importCustomer(LifecycleTransformer<ImageBean> ransformer, Dialog mDialog, File file,int imgWidth,int imgHeight){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);
        mRepository.importCustomer(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ransformer)
                .subscribe(imageBean -> {
                    if(mDialog != null && mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    if(imageBean.getCode() == 0){
                        if(mView != null){
                            mView.uploadPicSuccess(imageBean.getData(),imgWidth,imgHeight,file);
                        }
                    }else {
                        if (mView != null){
                            mView.uploadPicFailed(imageBean.getMsg());
                        }
                    }

                }, throwable -> {
                    if(mDialog != null && mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    if (mView != null){
                        mView.uploadPicFailed(throwable.getMessage());
                    }
                });
    }

}
