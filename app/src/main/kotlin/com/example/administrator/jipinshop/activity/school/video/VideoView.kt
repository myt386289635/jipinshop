package com.example.administrator.jipinshop.activity.school.video

import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.bean.SucBeanT
import okhttp3.ResponseBody

/**
 * @author 莫小婷
 * @create 2020/7/15
 * @Describe
 */
interface VideoView {

    fun onSuccess(bean: SucBeanT<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>)
    fun onFile(error : String?)

    fun onVideo(it : ResponseBody)
}