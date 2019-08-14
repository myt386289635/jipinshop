package com.example.administrator.jipinshop.activity.evakt.send.corve

import com.example.administrator.jipinshop.bean.SreachResultGoodsBean
import java.io.File

/**
 * @author 莫小婷
 * @create 2019/8/13
 * @Describe
 */
interface SubmitCorveView {

    fun onSave()
    fun onFile(error: String?)

    fun uploadPicSuccess(picPath: String, file: File)
    fun uploadPicFailed(error: String?)

    fun onRelatedGoods(bean : SreachResultGoodsBean)
    fun onDetele(position: Int)
}