package com.example.administrator.jipinshop.fragment.member

import com.example.administrator.jipinshop.bean.MemberBean

/**
 * @author 莫小婷
 * @create 2020/4/2
 * @Describe
 */
interface KTMemberView {

    fun onSuccess(bean : MemberBean)
    fun onFile(error:String?)

    fun onApply()
}