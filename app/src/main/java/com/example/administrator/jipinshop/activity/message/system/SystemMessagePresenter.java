package com.example.administrator.jipinshop.activity.message.system;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public class SystemMessagePresenter {

    Repository mRepository;

    @Inject
    public SystemMessagePresenter(Repository repository) {
        mRepository = repository;
    }


}
