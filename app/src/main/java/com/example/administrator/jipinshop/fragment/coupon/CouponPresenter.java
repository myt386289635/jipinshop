package com.example.administrator.jipinshop.fragment.coupon;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe
 */
public class CouponPresenter {

    Repository mRepository;

    @Inject
    public CouponPresenter(Repository repository) {
        mRepository = repository;
    }

}
