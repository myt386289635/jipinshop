package com.example.administrator.jipinshop.base;

import com.example.administrator.jipinshop.activity.KTArticleMoreActivity;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.address.MyAddressActivity;
import com.example.administrator.jipinshop.activity.address.add.CreateAddressActivity;
import com.example.administrator.jipinshop.activity.balance.MyWalletActivity;
import com.example.administrator.jipinshop.activity.balance.team.TeamActivity;
import com.example.administrator.jipinshop.activity.balance.withdraw.WithdrawActivity;
import com.example.administrator.jipinshop.activity.commenlist.CommenListActivity;
import com.example.administrator.jipinshop.activity.evakt.comparison.ComparActivity;
import com.example.administrator.jipinshop.activity.evakt.send.SubmitActivity;
import com.example.administrator.jipinshop.activity.evakt.send.corve.SubmitCorveActivity;
import com.example.administrator.jipinshop.activity.evakt.send.goods.AddGoodsActivity;
import com.example.administrator.jipinshop.activity.evakt.unbox.UnboxActivity;
import com.example.administrator.jipinshop.activity.follow.FollowActivity;
import com.example.administrator.jipinshop.activity.home.MainActivity;
import com.example.administrator.jipinshop.activity.home.article.ArticleDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.ClassifyActivity;
import com.example.administrator.jipinshop.activity.home.classification.article.ArticleMoreActivity;
import com.example.administrator.jipinshop.activity.home.classification.encyclopedias.EncyclopediasActivity;
import com.example.administrator.jipinshop.activity.home.classification.encyclopedias.detail.EncyclopediasDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.questions.QuestionsActivity;
import com.example.administrator.jipinshop.activity.home.classification.questions.detail.QuestionDetailActivity;
import com.example.administrator.jipinshop.activity.home.classification.questions.submit.QuestionSubmitActivity;
import com.example.administrator.jipinshop.activity.home.newarea.NewAreaActivity;
import com.example.administrator.jipinshop.activity.info.MyInfoActivity;
import com.example.administrator.jipinshop.activity.info.account.AccountManageActivity;
import com.example.administrator.jipinshop.activity.info.account.change.ChangePhone2Activity;
import com.example.administrator.jipinshop.activity.info.account.change.ChangePhoneActivity;
import com.example.administrator.jipinshop.activity.info.bind.BindNumberActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.activity.info.editsign.EditSignActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.mall.MallActivity;
import com.example.administrator.jipinshop.activity.mall.detail.MallDetailActivity;
import com.example.administrator.jipinshop.activity.mall.exchange.ExchangeActivity;
import com.example.administrator.jipinshop.activity.message.MessageActivity;
import com.example.administrator.jipinshop.activity.message.detail.MsgDetailActivity;
import com.example.administrator.jipinshop.activity.minekt.publishkt.detail.AuditDetailActivity;
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity;
import com.example.administrator.jipinshop.activity.order.MyOrderActivity;
import com.example.administrator.jipinshop.activity.order.detail.OrderDetailActivity;
import com.example.administrator.jipinshop.activity.report.cover.CoverReportActivity;
import com.example.administrator.jipinshop.activity.report.create.CreateReportActivity;
import com.example.administrator.jipinshop.activity.report.detail.ReportDetailActivity;
import com.example.administrator.jipinshop.activity.setting.SettingActivity;
import com.example.administrator.jipinshop.activity.setting.opinion.OpinionActivity;
import com.example.administrator.jipinshop.activity.shoppingdetail.ShoppingDetailActivity;
import com.example.administrator.jipinshop.activity.sign.SignActivity;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationActivity;
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity;
import com.example.administrator.jipinshop.activity.sreach.SreachActivity;
import com.example.administrator.jipinshop.activity.sreach.result.SreachResultActivity;
import com.example.administrator.jipinshop.activity.tryout.TryAllActivity;
import com.example.administrator.jipinshop.activity.tryout.detail.TryDetailActivity;
import com.example.administrator.jipinshop.activity.tryout.freedetail.FreeDetailActivity;
import com.example.administrator.jipinshop.activity.tryout.passedMore.PassedMoreActivity;
import com.example.administrator.jipinshop.activity.tryout.reportMore.ReportMoreActivity;
import com.example.administrator.jipinshop.activity.tryout.shareMore.ShareMoreActivity;
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity;
import com.example.administrator.jipinshop.activity.wellcome.WellComeActivity;
import com.example.administrator.jipinshop.auto.ActivityScope;
import com.example.administrator.jipinshop.netwrok.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface BaseActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(SreachActivity sreachActivity);
    void inject(ShoppingDetailActivity shoppingDetailActivity);
    void inject(CommenListActivity commenListActivity);
    void inject(LoginActivity loginActivity);
    void inject(BindNumberActivity bindNumberActivity);
    void inject(MessageActivity messageActivity);
    void inject(OpinionActivity activity);
    void inject(FollowActivity activity);
    void inject(IntegralDetailActivity activity);
    void inject(SreachResultActivity activity);
    void inject(ArticleDetailActivity activity);
    void inject(SettingActivity settingActivity);
    void inject(MyInfoActivity activity);
    void inject(EditNameActivity activity);
    void inject(SignActivity activity);
    void inject(MsgDetailActivity activity);
    void inject(AccountManageActivity activity);
    void inject(ChangePhoneActivity activity);
    void inject(ChangePhone2Activity activity);
    void inject(MyAddressActivity activity);
    void inject(CreateAddressActivity activity);
    void inject(MyWalletActivity activity);
    void inject(MyOrderActivity activity);
    void inject(MallActivity activity);
    void inject(MallDetailActivity activity);
    void inject(ExchangeActivity activity);
    void inject(InvitationActivity activity);
    void inject(TryAllActivity activity);
    void inject(TryDetailActivity activity);
    void inject(PassedMoreActivity activity);
    void inject(ReportMoreActivity activity);
    void inject(ShareMoreActivity activity);
    void inject(OrderDetailActivity activity);
    void inject(WellComeActivity activity);
    void inject(CreateReportActivity activity);
    void inject(CoverReportActivity activity);
    void inject(ReportDetailActivity activity);
    void inject(WithdrawActivity activity);
    void inject(TeamActivity activity);
    void inject(InvitationNewActivity activity);
    void inject(WebActivity activity);
    void inject(FreeDetailActivity activity);
    void inject(NewAreaActivity activity);
    void inject(ArticleMoreActivity activity);
    void inject(QuestionsActivity activity);
    void inject(QuestionDetailActivity activity);
    void inject(ClassifyActivity activity);
    void inject(QuestionSubmitActivity activity);
    void inject(EncyclopediasActivity activity);
    void inject(EncyclopediasDetailActivity activity);
    void inject(KTArticleMoreActivity activity);
    void inject(UnboxActivity activity);
    void inject(ComparActivity activity);
    void inject(SubmitActivity activity);
    void inject(SubmitCorveActivity activity);
    void inject(AddGoodsActivity activity);
    void inject(UserActivity activity);
    void inject(EditSignActivity activity);
    void inject(AuditDetailActivity activity);
    void inject(TaoBaoWebActivity activity);
}
