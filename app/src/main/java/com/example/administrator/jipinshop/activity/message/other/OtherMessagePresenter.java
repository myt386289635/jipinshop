package com.example.administrator.jipinshop.activity.message.other;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public class OtherMessagePresenter {

    Repository mRepository;

    @Inject
    public OtherMessagePresenter(Repository repository) {
        mRepository = repository;
    }

}
