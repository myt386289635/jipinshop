package com.example.administrator.jipinshop.activity.balance.score;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/8
 * @Describe
 */
public class ScorePresenter {

    Repository mRepository;

    @Inject
    public ScorePresenter(Repository repository) {
        mRepository = repository;
    }
}
