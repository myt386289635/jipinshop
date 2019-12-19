package com.example.administrator.jipinshop.activity.home

import com.example.administrator.jipinshop.bean.TBSreachResultBean

/**
 * @author 莫小婷
 * @create 2019/12/17
 * @Describe
 */
interface HomeDetailView {
    fun onSuccess(bean : TBSreachResultBean)
    fun onFile(error: String?)
}