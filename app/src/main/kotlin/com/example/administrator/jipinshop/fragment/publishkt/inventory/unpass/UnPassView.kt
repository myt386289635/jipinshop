package com.example.administrator.jipinshop.fragment.publishkt.inventory.unpass

import com.example.administrator.jipinshop.bean.SreachResultArticlesBean

/**
 * @author 莫小婷
 * @create 2019/8/23
 * @Describe
 */
interface UnPassView {
    fun onSuccess(bean : SreachResultArticlesBean)
    fun onFile(error: String?)

    fun onSave(position: Int)
    fun onFileCommon(error: String?)

    fun onDelete(position: Int)
}