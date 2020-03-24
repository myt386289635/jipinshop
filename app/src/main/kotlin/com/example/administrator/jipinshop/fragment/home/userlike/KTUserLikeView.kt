package com.example.administrator.jipinshop.fragment.home.userlike

import com.example.administrator.jipinshop.bean.SimilerGoodsBean

/**
 * @author 莫小婷
 * @create 2019/12/16
 * @Describe
 */
interface KTUserLikeView {

    fun onSuccess(bean: SimilerGoodsBean)
    fun onFile(error: String?)

}