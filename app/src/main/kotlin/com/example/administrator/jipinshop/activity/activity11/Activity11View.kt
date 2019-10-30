package com.example.administrator.jipinshop.activity.activity11

import com.example.administrator.jipinshop.bean.Activity11Bean
import com.example.administrator.jipinshop.bean.ImageBean

/**
 * @author 莫小婷
 * @create 2019/10/30
 * @Describe
 */
interface Activity11View {
    fun onSuccess(bean: Activity11Bean)
    fun onFile(error :String?)

    fun onBuyLinkSuccess(bean: ImageBean)
    fun onFileCommentInsert(error: String?)
}