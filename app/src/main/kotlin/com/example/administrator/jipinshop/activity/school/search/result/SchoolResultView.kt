package com.example.administrator.jipinshop.activity.school.search.result

import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.bean.SucBean

/**
 * @author 莫小婷
 * @create 2020/7/17
 * @Describe
 */
interface SchoolResultView {

    fun onSuccess(bean: SucBean<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>)
    fun onFile(error : String?)

}