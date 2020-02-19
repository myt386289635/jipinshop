package com.example.administrator.jipinshop.activity.money.withdraw

import com.example.administrator.jipinshop.netwrok.Repository
import javax.inject.Inject

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/19 12:12
 * Description：
 */
class MoneyWithdrawPresenter {

    private var repository : Repository

    @Inject
    constructor(repository: Repository){
        this.repository = repository
    }
}