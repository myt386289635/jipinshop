package com.example.administrator.jipinshop.fragment.sreach.find;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public class SreachFindPresenter {

    private Repository mRepository;

    @Inject
    public SreachFindPresenter(Repository repository) {
        mRepository = repository;
    }
}
