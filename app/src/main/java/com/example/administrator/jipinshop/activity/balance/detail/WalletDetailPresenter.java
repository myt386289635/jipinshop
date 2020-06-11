package com.example.administrator.jipinshop.activity.balance.detail;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe
 */
public class WalletDetailPresenter {

    private Repository mRepository;

    @Inject
    public WalletDetailPresenter(Repository repository) {
        mRepository = repository;
    }
}
