package com.example.administrator.jipinshop.base;

import com.example.administrator.jipinshop.auto.FragmentScope;
import com.example.administrator.jipinshop.fragment.coupon.CouponFragment;
import com.example.administrator.jipinshop.fragment.evaluation.EvaluationFragment;
import com.example.administrator.jipinshop.fragment.evaluation.common.CommonEvaluationFragment;
import com.example.administrator.jipinshop.fragment.find.FindFragment;
import com.example.administrator.jipinshop.fragment.find.common.CommonFindFragment;
import com.example.administrator.jipinshop.fragment.foval.FovalFragment;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.fragment.home.recommend.RecommendFragment;
import com.example.administrator.jipinshop.fragment.mine.MineFragment;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;

import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class)
public interface BaseFragmentComponent {
    void inject(HomeFragment fragment);
    void inject(RecommendFragment fragment);
    void inject(FindFragment fragment);
    void inject(EvaluationFragment fragment);
    void inject(CommonFindFragment fragment);
    void inject(CommonEvaluationFragment fragment);
    void inject(MineFragment fragment);
    void inject(FovalFragment fragment);
    void inject(CouponFragment fragment);
}
