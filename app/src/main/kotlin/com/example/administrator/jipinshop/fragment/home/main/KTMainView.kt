package com.example.administrator.jipinshop.fragment.home.main

import com.example.administrator.jipinshop.bean.ImageBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbkIndexBean

/**
 * @author 莫小婷
 * @create 2019/12/16
 * @Describe
 */
interface KTMainView {
    fun onSuccess(type: String,bean : TbkIndexBean)
    fun onFile(error: String?)
    fun onDay(bean : TBSreachResultBean)

    fun onShareSuc(bean: ImageBean)
    fun onShareFile()
}