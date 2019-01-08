package com.example.administrator.jipinshop.fragment.sreach.article;

import com.example.administrator.jipinshop.netwrok.Repository;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public class SreachArticlePresenter {

    private Repository mRepository;

    @Inject
    public SreachArticlePresenter(Repository repository) {
        mRepository = repository;
    }
}
