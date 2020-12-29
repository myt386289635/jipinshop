package com.example.administrator.jipinshop.activity.home.hot

import com.example.administrator.jipinshop.bean.TBSreachResultBean

/**
 * @author 莫小婷
 * @create 2020/12/29
 * @Describe
 */
interface HomeHotView {

    fun onSuccess(bean : TBSreachResultBean)
    fun onFile(error: String?)
}