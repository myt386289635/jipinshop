package com.example.administrator.jipinshop.activity.report.create;

import com.example.administrator.jipinshop.bean.ReportBean;

import java.io.File;

/**
 * @author 莫小婷
 * @create 2019/5/22
 * @Describe
 */
public interface CreateReportView {

    void onSuccessReport(ReportBean bean);
    void onFileReport(String error);

    /***上传图片**/
    void uploadPicSuccess(String picPath,int imgWidth,int imgHeight, File file);
    void uploadPicFailed(String error);
}
