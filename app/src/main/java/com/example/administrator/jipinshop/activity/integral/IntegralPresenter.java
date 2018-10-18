package com.example.administrator.jipinshop.activity.integral;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class IntegralPresenter {

    Repository mRepository;

    @Inject
    public IntegralPresenter(Repository repository) {
        mRepository = repository;
    }
}
