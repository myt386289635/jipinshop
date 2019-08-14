package com.example.administrator.jipinshop.activity.evakt.send

import com.example.administrator.jipinshop.bean.FindDetailBean
import java.io.File

/**
 * @author 莫小婷
 * @create 2019/8/13
 * @Describe
 */
interface SubmitView {

    fun onSuccess(bean: FindDetailBean)
    fun onFile(error: String?)

    fun onSave(bean : FindDetailBean)

    /***上传图片 */
    fun uploadPicSuccess(picPath: String, imgWidth: Int, imgHeight: Int, file: File)

    fun uploadPicFailed(error: String?)
}