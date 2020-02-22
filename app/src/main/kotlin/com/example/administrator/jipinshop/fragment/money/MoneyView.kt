package com.example.administrator.jipinshop.fragment.money

import com.example.administrator.jipinshop.bean.MoneyBean
import com.example.administrator.jipinshop.bean.MoneyPopBean
import com.example.administrator.jipinshop.bean.ShareInfoBean
import com.umeng.socialize.bean.SHARE_MEDIA

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/19 11:41
 * Description： 红包页面接口
 */
interface MoneyView {
    fun onSuccess(bean : MoneyBean)
    fun onFile(error:String?)

    fun onPic(type :String, share_media: SHARE_MEDIA? ,bean: ShareInfoBean)
    fun onCommenFile(error:String?)

    fun open(set: Int , money : Int)

    fun popInfo(bean: MoneyPopBean)

    fun openAll(money: String)
}