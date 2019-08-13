package com.example.administrator.jipinshop.activity.evakt.unbox

import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.bean.SucBean

/**
 * @author 莫小婷
 * @create 2019/8/13
 * @Describe
 */
interface UnboxView {

    fun onSuccess(bean: SucBean<EvaEvaBean.DataBean>)
    fun onFile(error :String?)
}