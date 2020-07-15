package com.example.administrator.jipinshop.fragment.school

import com.example.administrator.jipinshop.bean.SchoolHomeBean

/**
 * @author 莫小婷
 * @create 2020/7/14
 * @Describe
 */
interface KTSchoolView {

    fun onSuccess(bean : SchoolHomeBean)
    fun onFile(error: String?)
}