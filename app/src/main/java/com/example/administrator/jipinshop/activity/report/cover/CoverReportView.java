package com.example.administrator.jipinshop.activity.report.cover;

import java.io.File;

/**
 * @author 莫小婷
 * @create 2019/5/22
 * @Describe
 */
public interface CoverReportView {

    /***上传图片**/
    void uploadPicSuccess(String picPath, File file);
    void uploadPicFailed(String error);

    void onSuccessSave();
    void onFile(String error);
}
