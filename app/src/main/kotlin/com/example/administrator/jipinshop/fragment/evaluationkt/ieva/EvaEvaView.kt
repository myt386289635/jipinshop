package com.example.administrator.jipinshop.fragment.evaluationkt.ieva

import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.bean.EvaluationTabBean

/**
 * @author 莫小婷
 * @create 2019/8/7
 * @Describe
 */
interface EvaEvaView {

    fun tabClick(position: Int)

    fun onSuccess(bean : EvaluationTabBean)
    fun onFile(error : String?)

    fun onDateSuc(bean : EvaEvaBean)
    fun onDateFile(error : String?)
}