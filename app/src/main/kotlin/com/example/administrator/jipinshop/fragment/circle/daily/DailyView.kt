package com.example.administrator.jipinshop.fragment.circle.daily

import android.net.Uri
import com.example.administrator.jipinshop.bean.CircleListBean
import com.example.administrator.jipinshop.bean.CircleTitleBean
import com.umeng.socialize.bean.SHARE_MEDIA
import java.util.ArrayList

/**
 * @author 莫小婷
 * @create 2020/3/17
 * @Describe
 */
interface DailyView {
    fun onTabSuc(bean: CircleTitleBean)
    fun onTabFile(error: String?)

    fun onListSuc(bean: CircleListBean)
    fun onListFile(error: String?)

    fun onShareSuccess(shareImage : String , share_media: SHARE_MEDIA?)
    fun onShareFile(share_media: SHARE_MEDIA?)

    fun downLoadSuc(share_media: SHARE_MEDIA? , imageUris: ArrayList<Uri>)
}