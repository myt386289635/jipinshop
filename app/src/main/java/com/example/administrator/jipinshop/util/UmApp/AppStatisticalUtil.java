package com.example.administrator.jipinshop.util.UmApp;

import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.netwrok.Repository;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 用于后台统计用户行为
 */
public class AppStatisticalUtil {

    private Repository repository;

    @Inject
    public AppStatisticalUtil(Repository repository) {
        this.repository = repository;
    }

    public void addEvent(String eventId , LifecycleTransformer<SuccessBean> transformer){
        repository.addEvent(eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(successBean -> {}, throwable -> {});
    }

    /**
     * 统计底部tab的点击量
     */
    public void tab(int position ,  LifecycleTransformer<SuccessBean> transformer){
        switch (position){
            case 0:
                addEvent("shouye",transformer);
                break;
            case 1:
                addEvent("pingce",transformer);
                break;
            case 2:
                addEvent("bangdan",transformer);
                break;
            case 3:
                addEvent("faquan",transformer);
                break;
            case 4:
                addEvent("wode",transformer);
                break;
        }
    }

    /**
     * 首页推荐筛选统计
     */
    public void shouye_tj_tab(int position ,  LifecycleTransformer<SuccessBean> transformer){
        switch (position){
            case 0:
                addEvent("shouye_tuijian.zh",transformer);
                break;
            case 1:
                addEvent("shouye_tuijian.jg",transformer);
                break;
            case 2:
                addEvent("shouye_tuijian.bt",transformer);
                break;
            case 3:
                addEvent("shouye_tuijian.xl",transformer);
                break;
            case 4:
                addEvent("shouye_tuijian.zs",transformer);
                break;
        }
    }

    /**
     * 首页公共分类筛选统计
     */
    public void shouye_fl_tab(int position , String commenStatistical ,  LifecycleTransformer<SuccessBean> transformer){
        switch (position){
            case 0:
                addEvent(commenStatistical + "_liebiao.zh",transformer);
                break;
            case 1:
                addEvent(commenStatistical + "_liebiao.jg",transformer);
                break;
            case 2:
                addEvent(commenStatistical + "_liebiao.bt",transformer);
                break;
            case 3:
                addEvent(commenStatistical + "_liebiao.xl",transformer);
                break;
            case 4:
                addEvent(commenStatistical + "_liebiao.zs",transformer);
                break;
        }
    }

    /**
     * 专题页筛选统计
     */
    public void zhuanti_tab(int position  , String id,  LifecycleTransformer<SuccessBean> transformer){
        switch (position){
            case 0:
                addEvent("zhuanti."+id+"_zh",transformer);
                break;
            case 1:
                addEvent("zhuanti."+id+"_jg",transformer);
                break;
            case 2:
                addEvent("zhuanti."+id+"_bt",transformer);
                break;
            case 3:
                addEvent("zhuanti."+id+"_xl",transformer);
                break;
            case 4:
                addEvent("zhuanti."+id+"_zs",transformer);
                break;
        }
    }
}