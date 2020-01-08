package com.example.administrator.jipinshop.activity.cheapgoods

import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.NewPeopleBean
import com.example.administrator.jipinshop.bean.PopBean

/**
 * @author 莫小婷
 * @create 2020/1/6
 * @Describe
 */
interface CheapBuyView {

    fun onSuccess(bean: NewPeopleBean)
    fun onFile(error: String?)

    fun onBuySuccess(successBean: ImageBean)
    fun onBuyFile(error: String?)

    fun onDilaogSuc(bean : PopBean)
}