package com.example.administrator.jipinshop.activity.commenlist;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe
 */
public class CommenListPresenter {

    Repository mRepository;

    @Inject
    public CommenListPresenter(Repository repository) {
        mRepository = repository;
    }


}
