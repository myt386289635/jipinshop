package com.example.administrator.jipinshop.activity.evakt.send.goods

import com.example.administrator.jipinshop.bean.SreachResultGoodsBean

/**
 * @author 莫小婷
 * @create 2019/8/14
 * @Describe
 */
interface AddGoodsView {

    fun onSuccess(bean : SreachResultGoodsBean)
    fun onFile(error : String?)

    fun onAdd(position : Int)
    fun onDetele(position: Int)
    fun onCommonFile(error : String?)
}