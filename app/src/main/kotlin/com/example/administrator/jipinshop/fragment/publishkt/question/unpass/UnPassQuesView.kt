package com.example.administrator.jipinshop.fragment.publishkt.question.unpass

import com.example.administrator.jipinshop.bean.QuestionsBean

/**
 * @author 莫小婷
 * @create 2019/8/26
 * @Describe
 */
interface UnPassQuesView {
    fun onSuccess(articlesBean: QuestionsBean)
    fun onFile(error: String?)

    fun onFileCommon(error: String?)
    fun onDelete(position: Int)
}