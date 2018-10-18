package com.example.administrator.jipinshop.activity.integral.detail;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class IntegralDetailPresenter {

    Repository mRepository;

    @Inject
    public IntegralDetailPresenter(Repository repository) {
        mRepository = repository;
    }
}
