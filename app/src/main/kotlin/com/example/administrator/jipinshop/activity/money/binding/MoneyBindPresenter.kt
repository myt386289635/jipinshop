package com.example.administrator.jipinshop.activity.money.binding

import com.example.administrator.jipinshop.netwrok.Repository
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/2/22
 * @Describe
 */
class MoneyBindPresenter {

    private var mRepository: Repository

    @Inject
    constructor(mRepository: Repository) {
        this.mRepository = mRepository
    }


}