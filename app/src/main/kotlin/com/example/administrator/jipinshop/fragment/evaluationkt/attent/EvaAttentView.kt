package com.example.administrator.jipinshop.fragment.evaluationkt.attent

import com.example.administrator.jipinshop.bean.EvaAttentBean

/**
 * @author 莫小婷
 * @create 2019/8/8
 * @Describe
 */
interface EvaAttentView {

    fun onSuccess( bean : EvaAttentBean)
    fun onFile( error : String?)

    fun onAttent(pos : Int)
    fun onCancleAttent(pos : Int)
    fun commenFile(error : String?)
    fun onAttent2(pos : Int , fpos : Int)
    fun onCancleAttent2(pos : Int , fpos: Int)
}