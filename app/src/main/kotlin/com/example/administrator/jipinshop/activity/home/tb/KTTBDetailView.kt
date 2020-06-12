package com.example.administrator.jipinshop.activity.home.tb

import com.example.administrator.jipinshop.bean.SimilerGoodsBean

/**
 * @author 莫小婷
 * @create 2020/6/12
 * @Describe
 */
interface KTTBDetailView {

    fun onSuccess(bean: SimilerGoodsBean)
    fun onFile(error: String?)
}