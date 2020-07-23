package com.example.administrator.jipinshop.activity.school.video

import com.example.administrator.jipinshop.bean.*
import okhttp3.ResponseBody

/**
 * @author 莫小婷
 * @create 2020/7/15
 * @Describe
 */
interface VideoView {

    fun onSuccess(bean: VideoBean)
    fun onFile(error : String?)

    fun onVideo(it : ResponseBody)

    fun onVote(successBean: VoteBean)
    fun onDelVote(successBean: VoteBean)

    fun onList(bean: SucBean<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>)
}