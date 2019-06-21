package com.example.administrator.jipinshop.base;

import com.example.administrator.jipinshop.auto.FragmentScope;
import com.example.administrator.jipinshop.fragment.balance.budget.BudgetDetailFragment;
import com.example.administrator.jipinshop.fragment.balance.withdraw.WithdrawDetailFragment;
import com.example.administrator.jipinshop.fragment.evaluation.EvaluationFragment;
import com.example.administrator.jipinshop.fragment.evaluation.common.CommonEvaluationFragment;
import com.example.administrator.jipinshop.fragment.find.FindFragment;
import com.example.administrator.jipinshop.fragment.find.common.CommonFindFragment;
import com.example.administrator.jipinshop.fragment.follow.attention.AttentionFragment;
import com.example.administrator.jipinshop.fragment.follow.fans.FansFragment;
import com.example.administrator.jipinshop.fragment.foval.article.FovalArticleFragment;
import com.example.administrator.jipinshop.fragment.foval.find.FovalFindFragment;
import com.example.administrator.jipinshop.fragment.foval.goods.FovalGoodsFragment;
import com.example.administrator.jipinshop.fragment.foval.tryout.FovalTryFragment;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.fragment.home.commen.HomeCommenFragment;
import com.example.administrator.jipinshop.fragment.home.recommend.RecommendFragment;
import com.example.administrator.jipinshop.fragment.mine.MineFragment;
import com.example.administrator.jipinshop.fragment.sreach.article.SreachArticleFragment;
import com.example.administrator.jipinshop.fragment.sreach.find.SreachFindFragment;
import com.example.administrator.jipinshop.fragment.sreach.goods.SreachGoodsFragment;
import com.example.administrator.jipinshop.fragment.sreach.tryout.SreachTryFragment;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.FreeFragment;
import com.example.administrator.jipinshop.fragment.tryout.trymodel.TryFragment;
import com.example.administrator.jipinshop.fragment.tryout.hot.TryCommenFragment;
import com.example.administrator.jipinshop.fragment.tryout.mine.TrialCommonFragment;
import com.example.administrator.jipinshop.fragment.tryout.mine.TrialReportFragment;
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
    void inject(FovalArticleFragment fragment);
    void inject(AttentionFragment fragment);
    void inject(FansFragment fragment);
    void inject(HomeCommenFragment fragment);
    void inject(SreachGoodsFragment fragment);
    void inject(SreachFindFragment fragment);
    void inject(SreachArticleFragment fragment);
    void inject(FovalGoodsFragment fragment);
    void inject(FovalFindFragment fragment);
    void inject(BudgetDetailFragment fragment);
    void inject(TryFragment fragment);
    void inject(TryCommenFragment fragment);
    void inject(SreachTryFragment fragment);
    void inject(FovalTryFragment fragment);
    void inject(TrialCommonFragment fragment);
    void inject(TrialReportFragment fragment);
    void inject(WithdrawDetailFragment fragment);
    void inject(FreeFragment fragment);
}
