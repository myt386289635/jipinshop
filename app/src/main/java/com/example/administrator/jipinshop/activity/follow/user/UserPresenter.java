package com.example.administrator.jipinshop.activity.follow.user;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class UserPresenter {

    Repository mRepository;

    @Inject
    public UserPresenter(Repository repository) {
        mRepository = repository;
    }

}
