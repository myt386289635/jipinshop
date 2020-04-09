package com.example.administrator.jipinshop.activity.share

import android.net.Uri
import com.example.administrator.jipinshop.bean.ShareBean
import com.umeng.socialize.bean.SHARE_MEDIA
import java.util.ArrayList

/**
 * @author 莫小婷
 * @create 2020/3/20
 * @Describe
 */
interface ShareView {
    fun onSuccess(bean: ShareBean)
    fun onFile(error: String?)

    fun onRefresh(shareImage : String,type: String)

    fun initShareContent(checkBox1: Boolean , checkBox2: Boolean , checkBox3: Boolean)

    fun downLoadSuc(share_media: SHARE_MEDIA?, imageUris: ArrayList<Uri>)
}