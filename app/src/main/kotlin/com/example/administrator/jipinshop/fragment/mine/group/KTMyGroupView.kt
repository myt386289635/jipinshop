package com.example.administrator.jipinshop.fragment.mine.group

import com.example.administrator.jipinshop.bean.ShareInfoBean

/**
 * @author 莫小婷
 * @create 2020/11/27
 * @Describe
 */
interface KTMyGroupView {

    fun initShare(bean: ShareInfoBean)
    fun onFile(error: String?)
}