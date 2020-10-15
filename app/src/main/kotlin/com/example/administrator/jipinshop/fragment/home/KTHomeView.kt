package com.example.administrator.jipinshop.fragment.home

import com.example.administrator.jipinshop.bean.*

/**
 * @author 莫小婷
 * @create 2019/12/16
 * @Describe
 */
interface KTHomeView {

    fun onSuccess(bean: JDBean)
    fun onFile(error: String?)

    fun onAction(bean: SucBeanT<TbkIndexBean.DataBean.Ad1ListBean>?)
    fun onEndAction()
}