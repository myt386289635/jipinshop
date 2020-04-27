package com.example.administrator.jipinshop.activity.home.jd_pdd

import com.example.administrator.jipinshop.bean.JDBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean

/**
 * @author 莫小婷
 * @create 2020/4/26
 * @Describe
 */
interface KTJDDetailView {

    fun onSuccess(bean: JDBean)
    fun onFile(error: String?)

    fun onList(bean: TBSreachResultBean)
}