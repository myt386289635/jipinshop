package com.example.administrator.jipinshop.fragment.home

import com.example.administrator.jipinshop.bean.EvaluationTabBean

/**
 * @author 莫小婷
 * @create 2019/12/16
 * @Describe
 */
interface KTHomeView {

    fun onSuccess(bean: EvaluationTabBean)
    fun onFile(error: String?)
}