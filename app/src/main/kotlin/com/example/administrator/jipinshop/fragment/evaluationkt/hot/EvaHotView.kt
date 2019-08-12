package com.example.administrator.jipinshop.fragment.evaluationkt.hot

import com.example.administrator.jipinshop.bean.EvaHotBean

/**
 * @author 莫小婷
 * @create 2019/8/8
 * @Describe
 */
interface EvaHotView {

    fun onSuccess(bean : EvaHotBean)
    fun onFile(error : String?)

    fun onAttent(pos : Int)
    fun onCancleAttent(pos : Int)
    fun commenFile(error : String?)
}