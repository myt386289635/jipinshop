package com.example.administrator.jipinshop.activity.message;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public class MessagePresenter {

    Repository mRepository;

    @Inject
    public MessagePresenter(Repository repository) {
        mRepository = repository;
    }



}
