package com.example.administrator.jipinshop.activity.minekt.userkt

import com.example.administrator.jipinshop.bean.UserInfoBean

/**
 * @author 莫小婷
 * @create 2019/8/19
 * @Describe
 */
interface UserView {
    fun onSuccess( bean : UserInfoBean)
    fun onFile( error : String?)

    fun onAttent()
    fun onCancleAttent()
}