package com.example.administrator.jipinshop.fragment.userkt.article

import com.example.administrator.jipinshop.bean.QuestionsBean

/**
 * @author 莫小婷
 * @create 2019/8/20
 * @Describe
 */
interface UserArticleView {

     fun onSuccess(articlesBean: QuestionsBean)
     fun onFile(error: String?)
}