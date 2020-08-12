package com.example.administrator.jipinshop.fragment.home.main

import com.example.administrator.jipinshop.bean.ActionHBBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbkIndexBean

/**
 * @author 莫小婷
 * @create 2020/4/20
 * @Describe
 */
interface KTMain2View {
    fun onSuccess(type: String,bean : TbkIndexBean)
    fun onFile(error: String?)
    fun onDay(bean : TBSreachResultBean)
    fun onHBID(bean: ActionHBBean)
    fun onHBFlie()
}