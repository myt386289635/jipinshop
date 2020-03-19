package com.example.administrator.jipinshop.fragment.circle

import android.content.Context
import android.widget.LinearLayout
import com.example.administrator.jipinshop.netwrok.Repository
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/3/16
 * @Describe
 */
class CirclePresenter {

    private var repository: Repository

    @Inject
    constructor(repository: Repository) {
        this.repository = repository
    }

    fun setStatusBarHight(StatusBar: LinearLayout, context: Context) {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            val layoutParams = StatusBar.layoutParams
            layoutParams.height = statusBarHeight
        }
    }
}