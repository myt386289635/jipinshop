package com.example.administrator.jipinshop.fragment.activity11

import com.example.administrator.jipinshop.bean.Action11Bean
import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.SucBean

/**
 * @author 莫小婷
 * @create 2019/10/29
 * @Describe
 */
interface Action11View {
    fun onSuccess(bean : Action11Bean)
    fun onFile(error : String?)

    fun onPageSuccess(bean: SucBean<Action11Bean.DataBean.GoodsDataListBean>)
    fun onPageFile(error : String?)

    fun onBuyLinkSuccess(bean: ImageBean)
    fun onFileCommentInsert(error: String?)
}