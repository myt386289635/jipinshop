package com.example.administrator.jipinshop.activity.cheapgoods

import com.example.administrator.jipinshop.bean.NewPeopleBean
import com.example.administrator.jipinshop.bean.ShareInfoBean
import com.umeng.socialize.bean.SHARE_MEDIA

/**
 * @author 莫小婷
 * @create 2020/1/6
 * @Describe
 */
interface CheapBuyView {

    fun onSuccess(bean: NewPeopleBean)
    fun onFile(error: String?)

    fun initShare(share_media: SHARE_MEDIA?, bean: ShareInfoBean)
}