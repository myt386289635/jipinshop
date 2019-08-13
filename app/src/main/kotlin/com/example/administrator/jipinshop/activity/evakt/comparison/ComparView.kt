package com.example.administrator.jipinshop.activity.evakt.comparison

import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.bean.SucBean

/**
 * @author 莫小婷
 * @create 2019/8/13
 * @Describe
 */
interface ComparView {

    fun onSuccess(bean: SucBean<EvaEvaBean.List2Bean>)
    fun onFile(error :String?)
}