package com.example.administrator.jipinshop.activity.minekt.publishkt.detail

import com.example.administrator.jipinshop.bean.FindDetailBean
import com.example.administrator.jipinshop.bean.ImageBean

/**
 * @author 莫小婷
 * @create 2019/8/23
 * @Describe
 */
interface AuditDetailView {
     fun onSuccess(bean: FindDetailBean)
     fun onFile(error: String?)

     fun onBuyLinkSuccess(bean: ImageBean)
     fun onFileCommentInsert(error: String?)
}