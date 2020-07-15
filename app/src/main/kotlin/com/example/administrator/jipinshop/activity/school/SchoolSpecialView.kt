package com.example.administrator.jipinshop.activity.school

import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.bean.SucBean

/**
 * @author 莫小婷
 * @create 2020/7/15
 * @Describe
 */
interface SchoolSpecialView {
    fun onSuccess(bean: SucBean<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>)
    fun onFile(error : String?)
}