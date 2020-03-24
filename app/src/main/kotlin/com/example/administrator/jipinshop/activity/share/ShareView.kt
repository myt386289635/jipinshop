package com.example.administrator.jipinshop.activity.share

import com.example.administrator.jipinshop.bean.ShareBean

/**
 * @author 莫小婷
 * @create 2020/3/20
 * @Describe
 */
interface ShareView {
    fun onSuccess(bean: ShareBean)
    fun onFile(error: String?)

    fun onRefresh(shareImage : String)

    fun initShareContent(checkBox1: Boolean , checkBox2: Boolean , checkBox3: Boolean)
}