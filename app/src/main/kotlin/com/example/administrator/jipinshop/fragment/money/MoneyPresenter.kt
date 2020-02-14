package com.example.administrator.jipinshop.fragment.money

import android.content.Context
import android.widget.LinearLayout
import com.example.administrator.jipinshop.netwrok.Repository
import javax.inject.Inject

class MoneyPresenter {

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