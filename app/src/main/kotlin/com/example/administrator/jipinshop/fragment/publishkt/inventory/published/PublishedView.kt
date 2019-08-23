package com.example.administrator.jipinshop.fragment.publishkt.inventory.published

import com.example.administrator.jipinshop.bean.SreachResultArticlesBean

/**
 * @author 莫小婷
 * @create 2019/8/22
 * @Describe
 */
interface PublishedView {
    fun onSuccess(bean : SreachResultArticlesBean)
    fun onFile(error: String?)
}