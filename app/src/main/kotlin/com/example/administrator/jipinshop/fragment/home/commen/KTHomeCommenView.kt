package com.example.administrator.jipinshop.fragment.home.commen

import com.example.administrator.jipinshop.bean.TbCommonBean

/**
 * @author 莫小婷
 * @create 2019/12/17
 * @Describe
 */
interface KTHomeCommenView {

    fun onSuccess(bean: TbCommonBean)
    fun onFile(error: String?)
}