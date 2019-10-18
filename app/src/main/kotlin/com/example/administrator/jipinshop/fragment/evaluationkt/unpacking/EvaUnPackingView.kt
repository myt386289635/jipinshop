package com.example.administrator.jipinshop.fragment.evaluationkt.unpacking

import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.bean.SucBean

/**
 * @author 莫小婷
 * @create 2019/10/17
 * @Describe
 */
interface EvaUnPackingView {

    fun onSuccess(bean : SucBean<EvaEvaBean.DataBean>)
    fun onFile(error : String?)
}