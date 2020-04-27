package com.example.administrator.jipinshop.fragment.home

import com.example.administrator.jipinshop.bean.JDBean
import com.example.administrator.jipinshop.bean.TeacherBean

/**
 * @author 莫小婷
 * @create 2019/12/16
 * @Describe
 */
interface KTHomeView {

    fun onSuccess(bean: JDBean)
    fun onFile(error: String?)

    fun onTeacher(type : Int,bean : TeacherBean)
}