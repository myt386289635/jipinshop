package com.example.administrator.jipinshop.fragment.evaluationkt.inventory

import com.example.administrator.jipinshop.bean.EvaluationListBean
import com.example.administrator.jipinshop.bean.EvaluationTabBean

/**
 * @author 莫小婷
 * @create 2019/8/8
 * @Describe
 */
interface EvaInventoryView {

    fun tabClick(position: Int)

    fun onSuccess(bean : EvaluationTabBean)
    fun onFile(error : String?)

    fun onDateSuc(bean : EvaluationListBean)
    fun onDateFile(error : String?)
}