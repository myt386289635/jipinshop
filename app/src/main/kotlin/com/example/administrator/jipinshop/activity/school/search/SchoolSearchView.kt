package com.example.administrator.jipinshop.activity.school.search

import com.example.administrator.jipinshop.bean.SreachHistoryBean

/**
 * @author 莫小婷
 * @create 2020/7/17
 * @Describe
 */
interface SchoolSearchView {

    fun onSuccess( bean: SreachHistoryBean)
    fun onFile(error : String?)

    fun jump(content: String)

    fun onDelect()
}