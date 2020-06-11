package com.example.administrator.jipinshop.fragment.mine.team

import com.example.administrator.jipinshop.bean.FansBean
import com.example.administrator.jipinshop.bean.SubUserBean

/**
 * @author 莫小婷
 * @create 2020/6/10
 * @Describe
 */
interface KTTeamView {

    fun onSuccess(bean : FansBean)
    fun onFile(error : String?)

    fun subDetail(bean: SubUserBean)
}