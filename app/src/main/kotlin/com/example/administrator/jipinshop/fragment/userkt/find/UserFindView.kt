package com.example.administrator.jipinshop.fragment.userkt.find

import com.example.administrator.jipinshop.bean.SreachResultArticlesBean

/**
 * @author 莫小婷
 * @create 2019/8/20
 * @Describe
 */
interface UserFindView {
    fun onSuccess( bean : SreachResultArticlesBean)
    fun onFile(error : String?)
}