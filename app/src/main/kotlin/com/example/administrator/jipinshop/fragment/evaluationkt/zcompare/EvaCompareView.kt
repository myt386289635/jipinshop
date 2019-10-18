package com.example.administrator.jipinshop.fragment.evaluationkt.zcompare

import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.bean.SucBean

/**
 * @author 莫小婷
 * @create 2019/10/17
 * @Describe
 */
interface EvaCompareView {
    fun onSuccess(bean : SucBean<EvaEvaBean.List2Bean>)
    fun onFile(error : String?)
}