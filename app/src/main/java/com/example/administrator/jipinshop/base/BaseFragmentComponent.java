package com.example.administrator.jipinshop.base;

import com.example.administrator.jipinshop.auto.FragmentScope;
import com.example.administrator.jipinshop.fragment.balance.budget.BudgetDetailFragment;
import com.example.administrator.jipinshop.fragment.balance.withdraw.WithdrawDetailFragment;
import com.example.administrator.jipinshop.fragment.circle.CircleFragment;
import com.example.administrator.jipinshop.fragment.circle.daily.DailyFragment;
import com.example.administrator.jipinshop.fragment.evaluation.EvaluationFragment;
import com.example.administrator.jipinshop.fragment.evaluation.common.CommonEvaluationFragment;
import com.example.administrator.jipinshop.fragment.evaluationkt.attent.EvaAttentFrament;
import com.example.administrator.jipinshop.fragment.evaluationkt.hot.EvaHotFragment;
import com.example.administrator.jipinshop.fragment.evaluationkt.ieva.EvaEvaFragment;
import com.example.administrator.jipinshop.fragment.evaluationkt.inventory.EvaInventoryFragment;
import com.example.administrator.jipinshop.fragment.evaluationkt.unpacking.EvaUnPackingFragment;
import com.example.administrator.jipinshop.fragment.evaluationkt.zcompare.EvaCompareFragment;
import com.example.administrator.jipinshop.fragment.find.FindFragment;
import com.example.administrator.jipinshop.fragment.find.common.CommonFindFragment;
import com.example.administrator.jipinshop.fragment.follow.attention.AttentionFragment;
import com.example.administrator.jipinshop.fragment.follow.fans.FansFragment;
import com.example.administrator.jipinshop.fragment.foval.article.FovalArticleFragment;
import com.example.administrator.jipinshop.fragment.foval.find.FovalFindFragment;
import com.example.administrator.jipinshop.fragment.foval.goods.FovalGoodsFragment;
import com.example.administrator.jipinshop.fragment.foval.tryout.FovalTryFragment;
import com.example.administrator.jipinshop.fragment.freekt.end.PayPendedFragment;
import com.example.administrator.jipinshop.fragment.freekt.pay.PayPendingFragment;
import com.example.administrator.jipinshop.fragment.home.HomeFragment;
import com.example.administrator.jipinshop.fragment.home.HomeNewFragment;
import com.example.administrator.jipinshop.fragment.home.KTHomeFragnent;
import com.example.administrator.jipinshop.fragment.home.commen.HomeCommenFragment;
import com.example.administrator.jipinshop.fragment.home.commen.KTHomeCommenFragment;
import com.example.administrator.jipinshop.fragment.home.main.KTMain2Fragment;
import com.example.administrator.jipinshop.fragment.home.recommend.RecommendFragment;
import com.example.administrator.jipinshop.fragment.home.userlike.KTUserLikeFragment;
import com.example.administrator.jipinshop.fragment.member.KTMemberFragment;
import com.example.administrator.jipinshop.fragment.mine.KTMineFragment;
import com.example.administrator.jipinshop.fragment.mine.team.TeamOneFragment;
import com.example.administrator.jipinshop.fragment.mine.team.three.TeamThreeFragment;
import com.example.administrator.jipinshop.fragment.mine.team.two.TeamTwoFragment;
import com.example.administrator.jipinshop.fragment.money.MoneyFragment;
import com.example.administrator.jipinshop.fragment.orderkt.KTMyOrderFragment;
import com.example.administrator.jipinshop.fragment.publishkt.inventory.published.PublishedFragment;
import com.example.administrator.jipinshop.fragment.publishkt.inventory.unpass.UnPassFragment;
import com.example.administrator.jipinshop.fragment.publishkt.question.pass.PassQuesFragment;
import com.example.administrator.jipinshop.fragment.publishkt.question.unpass.UnPassQuesFragment;
import com.example.administrator.jipinshop.fragment.school.KTSchoolFragment;
import com.example.administrator.jipinshop.fragment.sreach.article.SreachArticleFragment;
import com.example.administrator.jipinshop.fragment.sreach.find.SreachFindFragment;
import com.example.administrator.jipinshop.fragment.sreach.goods.SreachGoodsFragment;
import com.example.administrator.jipinshop.fragment.sreach.tryout.SreachTryFragment;
import com.example.administrator.jipinshop.fragment.userkt.article.UserArticleFragment;
import com.example.administrator.jipinshop.fragment.userkt.find.UserFindFragment;
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
    void inject(SreachTryFragment fragment);
    void inject(FovalTryFragment fragment);
    void inject(WithdrawDetailFragment fragment);
    void inject(HomeNewFragment fragment);
    void inject(EvaAttentFrament fragment);
    void inject(EvaHotFragment fragment);
    void inject(EvaEvaFragment fragment);
    void inject(EvaInventoryFragment fragment);
    void inject(UserFindFragment fragment);
    void inject(UserArticleFragment fragment);
    void inject(PublishedFragment fragment);
    void inject(UnPassFragment fragment);
    void inject(PassQuesFragment fragment);
    void inject(UnPassQuesFragment fragment);
    void inject(PayPendingFragment fragment);
    void inject(PayPendedFragment fragment);
    void inject(KTMyOrderFragment fragment);
    void inject(EvaUnPackingFragment fragment);
    void inject(EvaCompareFragment fragment);
    void inject(KTHomeFragnent fragment);
    void inject(KTUserLikeFragment fragment);
    void inject(KTHomeCommenFragment fragment);
    void inject(MoneyFragment fragment);
    void inject(CircleFragment fragment);
    void inject(DailyFragment fragment);
    void inject(KTMemberFragment fragment);
    void inject(KTMain2Fragment fragment);
    void inject(KTMineFragment fragment);
    void inject(TeamOneFragment fragment);
    void inject(TeamTwoFragment fragment);
    void inject(TeamThreeFragment fragment);
    void inject(KTSchoolFragment fragment);
}
