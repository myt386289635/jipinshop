package com.example.administrator.jipinshop.activity.family;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/9/14
 * @Describe
 */
public class FamilyPresenter {

    private Repository mRepository;

    @Inject
    public FamilyPresenter(Repository repository) {
        mRepository = repository;
    }


}
